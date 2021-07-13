package com.dcat.ReCo.services.ml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.recommendation.Rating;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dcat.ReCo.dtos.restaurant.RestaurantOnlyName;
import com.dcat.ReCo.dtos.user.UserProfileUpdateDTO;
import com.dcat.ReCo.models.RestaurantAndFeature;
import com.dcat.ReCo.models.RestaurantAndListTag;
import com.dcat.ReCo.models.ml.ItemDistance;
import com.dcat.ReCo.models.ml.ProfileDistance;
import com.dcat.ReCo.models.ml.UserCollab;
import com.dcat.ReCo.repositories.RestaurantRepository;
import com.dcat.ReCo.repositories.UserRepository;
import com.dcat.ReCo.repositories.ml.ItemDistanceRepository;
import com.dcat.ReCo.repositories.ml.ProfileDistanceRepository;
import com.dcat.ReCo.repositories.ml.UserCollabRepository;
import com.dcat.ReCo.vos.CollabUserVO;
import com.dcat.ReCo.vos.CollabUserVO.CollabRestaurantVO;
import com.dcat.ReCo.vos.CollabUserVO.OtherVO;
import com.dcat.ReCo.vos.CommentRateVO;
import com.dcat.ReCo.vos.EuclidVO;
import com.dcat.ReCo.vos.MovieVO;
import com.dcat.ReCo.vos.RatingVO;
import com.dcat.ReCo.vos.RestaurantVO;
import com.dcat.ReCo.vos.TagVO;

@Service
public class SparkServiceImpl implements SparkService {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private ProfileDistanceRepository profileDistanceRepository;

	@Autowired
	private ItemDistanceRepository itemDistanceRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserCollabRepository collabRepository;

	private SparkSession spark;
	private SparkConf    sc;
	SQLContext           sql;

	@Value("${spring.datasource.url}")
	private String sqlSource;
	@Value("${spring.datasource.username}")
	private String sqlUsername;
	@Value("${spring.datasource.password}")
	private String sqlPassword;
	@Value("${spring.datasource.driver-class-name}")
	private String driver;

	private Properties connectionProperties;

	public SparkServiceImpl() {
		// General
		//System.setProperty("hadoop.home.dir", "C:\\Program Files\\had");
		System.setProperty("spark.sql.crossJoin.enabled", "true");
		sc    = new SparkConf().setMaster("local[2]");
		sc.set("spark.driver.memory", "2147480000");
		sc.set("spark.testing.memory", "2147480000");
		spark = SparkSession
				.builder()
				.config(sc)
				.appName("ReCo Recommender")
				.getOrCreate();
		sql   = new SQLContext(spark);

		// For SQL
		connectionProperties = new Properties();
		connectionProperties.put("user", "root");
		connectionProperties.put("password", "1234");
	}

	@Override
	public void doContentBasedProfile(
			UserProfileUpdateDTO dto) {
		List<RestaurantAndListTag> features = featurizeRestaurant();

		calProfileDistance(features, dto);

	}
	
	public void doAll() {
		List<RestaurantOnlyName> rs = restaurantRepository.findBy(RestaurantOnlyName.class);
		
		for(RestaurantOnlyName r : rs) {
			doContentBasedItem(r.getId());
		}
	}

	@Override
	public void doContentBasedItem(
			Long restaurantId) {
		RestaurantAndFeature       item     = restaurantRepository
				.findById(restaurantId, RestaurantAndFeature.class);
		List<RestaurantAndFeature> features = featurizeRestaurant(restaurantId);
		for (int i = 0; i < 10; i++) {
			System.out.println(features.get(i).getId() + "\t"
					+ features.get(i).getMinPrice()
					+ "\t" + features.get(i).getMaxPrice() + "\t"
					+ features.get(i).getStarAverage() + "\t"
					+ features.get(i).getStarAmbiance() + "\t"
					+ features.get(i).getStarAverage() + "\t"
					+ features.get(i).getStarFood() + "\t"
					+ features.get(i).getStarNoise() + "\t" +
					features.get(i).getStarService() + "\t"
					+ features.get(i).getAddress().getDistrict().getId());
		}
		 calItemDistance(item, features);
	}

	public void doUserCollab2(Long userId) {
		//
		Dataset<Row> commentRateDS = sql.read().format("jdbc")
				.option("url", sqlSource)
				.option("driver", driver)
				.option("dbtable",
						"(select distinct c.user_id, c2.restaurant_id, (select avg(z.overall_star) from comments z where z.user_id = c.user_id and z.restaurant_id = c2.restaurant_id) rating from comments c, comments c2 order by c.user_id asc) commentRate")
				.option("user", sqlUsername)
				.option("password", sqlPassword)
				.load();

		List<CommentRateVO> rawData = commentRateDS.javaRDD()
				.map(row -> new CommentRateVO((long) row.getInt(0),
						(long) row.getInt(1),
						row.get(2) != null
								? Double.parseDouble(row.get(2).toString())
								: 0))
				.collect();
		System.out.println(rawData.size());
		List<CollabUserVO> matrix = new ArrayList<CollabUserVO>();

		for (CommentRateVO v : rawData) {
			CollabUserVO currentUserRating = getCurrentUserRating(matrix,
					v.getUserId());

			if (currentUserRating != null) {
				matrix.get(matrix.indexOf(currentUserRating)).addNewRating(
						v.getRestaurantId(),
						v.getRating());
			} else {
				currentUserRating = new CollabUserVO();
				currentUserRating.setUserId(v.getUserId());
				currentUserRating.addNewRating(v.getRestaurantId(),
						v.getRating());
				matrix.add(currentUserRating);
			}

		}
		// StringBuilder header = new StringBuilder();
		// header.append("4" + "\t");
		// header.append("6" + "\t");
		// header.append("15" + "\t");
		// System.out.println(header.toString());
		// for (CollabUserVO x : matrix) {
		// StringBuilder body = new StringBuilder();
		// for(CollabUserVO.CollabRestaurantVO r : x.getRestaurants()) {
		// body.append(r.getRating() + "\t");
		// }
		// System.out.println(body.toString());
		// }
		//
		CollabUserVO currentU = getCurrentUserRating(matrix, userId);
		if (currentU == null)
			return;

		// get list unrated restaurant
		List<CollabRestaurantVO> notRatedRes = currentU.getRestaurants()
				.stream()
				.filter(res -> res.getRating().equals(0.0))
				.collect(Collectors.toList());

		matrix = matrix.stream().map(m -> {
			m.normalize();
			return m;
		}).collect(Collectors.toList());

		// for (CollabUserVO x : matrix) {
		// StringBuilder body = new StringBuilder();
		// for(CollabUserVO.CollabRestaurantVO r : x.getRestaurants()) {
		// body.append(r.getRating() + "\t");
		// }
		// System.out.println(body.toString());
		// }
		//
		// calculate user similarity
		for (CollabUserVO item : matrix) {
			for (CollabUserVO item2 : matrix) {
				if (item2.getUserId() == item.getUserId()) {
					continue;
				} else {
					double tu   = 0;
					double mau1 = 0;
					double mau2 = 0;
					for (int i = 0; i < item.getRestaurants().size(); i++) {
						tu   += item.getRestaurants().get(i).getRating()
								* item2.getRestaurants().get(i).getRating();
						mau1 += item.getRestaurants().get(i).getRating()
								* item.getRestaurants().get(i).getRating();
						mau2 += item2.getRestaurants().get(i).getRating()
								* item2.getRestaurants().get(i).getRating();

					}

					double mau = (Math.sqrt(mau1) * Math.sqrt(mau2)) == 0 ? 1
							: (Math.sqrt(mau1) * Math.sqrt(mau2));

					double distance = tu / mau;
					item.addNewUserDistance(item2.getUserId(), distance);
				}

			}
		}
//		List<Long> ids = matrix.stream().map(u -> u.getUserId())
//				.collect(Collectors.toList());
//		for (CollabUserVO x : matrix) {
//			StringBuilder body = new StringBuilder();
//			for (Long uid : ids) {
//				Optional<CollabUserVO.OtherVO> ovo = x.getOthers().stream()
//						.filter(xx -> xx.getUserId().equals(uid))
//						.findFirst();
//				body.append(ovo.isPresent() == false ? "1\t" : ovo.get().getDistance()+"\t");
//			}
//			System.out.println(body.toString());
//		}
		
		 // get 2 user most related with current
		 // in this step has distance
		 currentU = getCurrentUserRating(matrix, userId);
		 // System.out.println("Current with disntance");
		 // System.out.println(currentU);
		 List<OtherVO> top2Related = new ArrayList<CollabUserVO.OtherVO>();
		 top2Related.addAll(currentU.getOthers());
		
		 Collections.sort(top2Related, new Comparator<OtherVO>() {
		 @Override
		 public int compare(OtherVO o1, OtherVO o2) {
		 // desc
		 return o2.getDistance() - o1.getDistance() > 0 ? 1
		 : o2.getDistance() - o1.getDistance() == 0 ? 0 : -1;
		 }
		 });
		 // top 2 related users
		 top2Related = top2Related.stream().limit(2)
		 .collect(Collectors.toList());
		 // get list items not rated by current user
		 // for each item, get rating of 2 related user
		 // cal
		
		 notRatedRes = notRatedRes.stream().map(res -> {
		 res.setRating(0.0);
		 return res;
		 }).collect(Collectors.toList());
		 System.out.println("PREDICT ALL ========");
		
		 for (CollabRestaurantVO vo : notRatedRes) {
		 System.out.println(vo);
		 double numerator = 0;
		 double denominator = 0;
		 for (OtherVO other : top2Related) {
		 denominator += Math.abs(other.getDistance());
		 // get CollabUserVO
		 CollabUserVO otherVO = getCurrentUserRating(matrix,
		 other.getUserId());
		 numerator += other.getDistance()
		 * otherVO.getRatingForRestaurant(vo.getRestaurantId());
		 }
		
		 // avoid denominator is 0
		 denominator += 0.0001;
		
		 // predict
		 notRatedRes.get(notRatedRes.indexOf(vo))
		 .setRating(numerator / denominator + currentU.getAvg());
		 }
		 ;
		
		 currentU.setPredictRestaurants(notRatedRes);
		 System.out.println("RESULT------------------------------------------");
		 System.out.println(currentU);
		
		 // save to db
		 collabRepository.deleteByUserId(userId);
		 List<UserCollab> results = new ArrayList<UserCollab>();
		 for (CollabRestaurantVO predict : currentU.getPredictRestaurants()) {
		 UserCollab collab = new UserCollab();
		 collab.setUser(userRepository.findById(userId).get());
		 collab.setRestaurant(restaurantRepository
		 .findById(predict.getRestaurantId()).get());
		 collab.setPredict(predict.getRating());
		 results.add(collab);
		 }
		 collabRepository.saveAll(results);

	}

	private CollabUserVO getCurrentUserRating(List<CollabUserVO> matrix,
			Long userId) {
		for (CollabUserVO v : matrix) {
			System.out.println(v.getUserId());
			if (v.getUserId().equals(userId)) {
				return v;
			}
		}

		return null;
	}

	public List<Rating> doUserCollab(Integer userId) {
		// use ALS
		// firstly load user rate restaurant
		Dataset<Row> commentRateDS = sql.read().format("jdbc")
				.option("url", sqlSource)
				.option("driver", driver)
				.option("dbtable",
						"(select c.user_id, c.restaurant_id, avg(c.overall_star) from comments c group by c.user_id, c.restaurant_id) commentRate")
				.option("user", sqlUsername)
				.option("password", sqlPassword)
				.load();
		// map to (u, r, rate)
		System.out.println("0---------------------------");

		JavaRDD<Rating> ratings = commentRateDS.javaRDD().map(row -> {
			Rating vo = new Rating(row.getInt(0), row.getInt(1),
					row.getDouble(2));
			return vo;
		});

		Dataset<Row> z = spark.createDataFrame(ratings,
				RatingVO.class);

		z.show();
		return null;

		// System.out.println("1---------------------------");
		//
		// // use ALS here
		// MatrixFactorizationModel model = ALS.trainImplicit(
		// JavaRDD.toRDD(ratings),
		// 10, 10);
		//
		// System.out.println("2---------------------------");
		//
		// // Create user-item tuples from ratings
		// JavaRDD<Tuple2<Object, Object>> userProducts = ratings
		// .map(r -> {
		// return new Tuple2<Object, Object>(r.user(), r.product());
		// });
		//
		// System.out.println("3---------------------------");
		//
		// // Calculate the itemIds not rated by a particular user
		// Dataset<Row> notRatedYetRestaurantIdDS = sql.read().format("jdbc")
		// .option("url", sqlSource)
		// .option("driver", driver)
		// .option("dbtable",
		// "(select r.id from restaurants r where r.id not in (select
		// c.restaurant_id from comments c where c.user_id = "
		// + userId + ")) commentRate")
		// .option("user", sqlUsername)
		// .option("password", sqlPassword)
		// .load();
		//
		// System.out.println("4---------------------------");
		//
		// JavaRDD<Integer> notRatedByUser = notRatedYetRestaurantIdDS.javaRDD()
		// .map(r -> r.getInt(0));
		//
		// System.out.println("5---------------------------");
		//
		// // Create user-item tuples for the items that are not rated by user
		// JavaRDD<Tuple2<Object, Object>> itemsNotRatedByUser = notRatedByUser
		// .map(restId -> new Tuple2<Object, Object>(userId, restId));
		//
		// System.out.println("6---------------------------");
		//
		// // Predict the ratings of the items not rated by user for the user
		// JavaRDD<Rating> recommendations = model
		// .predict(itemsNotRatedByUser.rdd()).toJavaRDD().distinct();
		//
		// recommendations.foreach(r -> {
		// String str = "User : " + r.user() + " Product : " +
		// r.product() +
		// "Rating : " +
		// r.rating();
		// System.out.println(str);
		//
		// });
		//
		// return recommendations.take(10);

	}

	public List<RestaurantAndListTag> featurizeRestaurant() {

		return restaurantRepository.findBy(RestaurantAndListTag.class);
	}

	public List<RestaurantAndFeature> featurizeRestaurant(Long id) {

		return restaurantRepository.findByIdNot(id, RestaurantAndFeature.class);
	}

	public JavaRDD<TagVO> loadTag() {
		sql = new SQLContext(spark);
		Dataset<Row> tagRS = sql.read().jdbc(sqlSource, "tags",
				connectionProperties);

		JavaRDD<TagVO> tagRDD = tagRS.javaRDD().map(row -> {
			TagVO tagVO = new TagVO();
			tagVO.setTagId(row.getLong(0));
			return tagVO;
		});

		return tagRDD;
	}

	public JavaRDD<RestaurantVO> loadRestaurant() {
		sql = new SQLContext(spark);
		Dataset<Row> restaurantRS = sql.read().format("jdbc")
				.option("url", sqlSource)
				.option("driver", driver)
				.option("dbtable",
						"(select r.id, a.district from restaurants r join address a on r.address_id = a.id) redi")
				.option("user", sqlUsername)
				.option("password", sqlPassword)
				.load();

		JavaRDD<RestaurantVO> restaurantRDD = restaurantRS.javaRDD()
				.map(row -> {
					RestaurantVO restaurantVO = new RestaurantVO();
					restaurantVO.setRestaurantId(row.getLong(0));
					restaurantVO.setDistrictId(row.getLong(1));
					return restaurantVO;
				});

		return restaurantRDD;
	}

	public JavaRDD<RatingVO> loadRating() {
		JavaRDD<RatingVO> ratingsRDD =

				spark.read().textFile("data/u.data").javaRDD()

						.map(row -> {

							RatingVO rvo = RatingVO.parseRating(row);

							return rvo;

						});

		return ratingsRDD;
	}

	public JavaRDD<MovieVO> loadMovie() {
		JavaRDD<MovieVO> moviesRDD =

				spark.read().textFile("data/u.item").javaRDD()

						.map(row -> {

							MovieVO mvo = MovieVO.parse(row);

							return mvo;

						});

		return moviesRDD;
	}

	public void calProfileDistance(
			List<RestaurantAndListTag> feature, UserProfileUpdateDTO dto) {

		// check if has record in profile_distance table mean that
		// profile has established, just get reference to update
		boolean hasEstablished = profileDistanceRepository
				.existsByUserId(dto.getUserId());

		List<ProfileDistance> euclidJava = feature.stream().map(row -> {

			ProfileDistance profile = new ProfileDistance();

			if (hasEstablished) {
				profile = profileDistanceRepository.findByUserIdAndRestaurantId(
						dto.getUserId(), row.getId()).get();
			}

			profile.setUser(userRepository.findById(dto.getUserId()).get());
			profile.setRestaurant(
					restaurantRepository.findById(row.getId()).get());
			// create set to hold all tag occur in both res and profile

			// pre-process
			List<Long> lsTagWithId = row.getTags().stream().map(f -> f.getId())
					.collect(Collectors.toList());
			//
			List<Long> valueableFeatures = getDiffOfTwoArray(lsTagWithId,
					dto.getTagId());

			double pow = 0;

			for (Long tagId : valueableFeatures) {
				pow += 1; // mean this is difference tag between two things.
			}

			// for district
			if (!dto.getAreas().contains(
					row.getAddress().getDistrict().getId().toString())) {
				// mean that the profile hasnt interest with this restaurant (by
				// district)
				pow += 1;
			}

			profile.setDistance(Math.sqrt(pow));

			return profile;
		}).collect(Collectors.toList());

		profileDistanceRepository.saveAll(euclidJava);
	}

	public void calItemDistance(RestaurantAndFeature item,
			List<RestaurantAndFeature> features) {
		boolean hasEstablished = itemDistanceRepository
				.existsByRestaurantBaseId(item.getId());

		List<Long> lsTagWithId1 = item.getTags().stream().map(f -> f.getId())
				.collect(Collectors.toList());

		List<ItemDistance> distances = features.stream().map(feature -> {
			ItemDistance distance = new ItemDistance();
			distance.setRestaurantBase(
					restaurantRepository.findById(item.getId()).get());
			distance.setRestaurantChild(
					restaurantRepository.findById(feature.getId()).get());

			if (hasEstablished) {
				distance = itemDistanceRepository.findById(item.getId()).get();
			}

			List<Long> lsTagWithId2 = feature.getTags().stream()
					.map(f -> f.getId())
					.collect(Collectors.toList());

			List<Long> valueableFeatures = getDiffOfTwoArray(lsTagWithId1,
					lsTagWithId2);

			List<Double> featurePows = new ArrayList<Double>();

			double pow = 0;

			for (Long tagId : valueableFeatures) {
				pow += 1; // mean this is difference tag between two things.
			}

			if (item.getAddress().getDistrict().getId() == feature.getAddress()
					.getDistrict().getId()) {
				pow += 1;
			}

			// start1 -> start5
			double start1 = Math.abs(item.getStar1() - feature.getStar1());
			double start2 = Math.abs(item.getStar2() - feature.getStar2());
			double start3 = Math.abs(item.getStar3() - feature.getStar3());
			double start4 = Math.abs(item.getStar4() - feature.getStar4());
			double start5 = Math.abs(item.getStar5() - feature.getStar5());

			double starAvg      = Math
					.abs(item.getStarAverage() - feature.getStarAverage());
			double starFood     = Math
					.abs(item.getStarFood() - feature.getStarFood());
			double starService  = Math
					.abs(item.getStarService() - feature.getStarService());
			double starAmbiance = Math
					.abs(item.getStarAmbiance() - feature.getStarAmbiance());
			double starNoise    = Math
					.abs(item.getStarNoise() - feature.getStarNoise());

			double minPrice = Math
					.abs(item.getMinPrice() / 1000.0
							- feature.getMinPrice() / 1000.0);
			double maxPrice = Math
					.abs(item.getMaxPrice() / 1000.0
							- feature.getMaxPrice() / 1000.0);

			featurePows.add(start1);
			featurePows.add(start2);
			featurePows.add(start3);
			featurePows.add(start4);
			featurePows.add(start5);
			featurePows.add(starAvg);
			featurePows.add(starFood);
			featurePows.add(starService);
			featurePows.add(starAmbiance);
			featurePows.add(starNoise);
			featurePows.add(minPrice);
			featurePows.add(maxPrice);

			for (Double d : featurePows) {
				pow += d * d;
			}

			distance.setDistance(Math.sqrt(pow));

			return distance;

		}).collect(Collectors.toList());

		itemDistanceRepository.saveAll(distances);
	}

	private List<Long> getDiffOfTwoArray(List<Long> l1, List<Long> l2) {
		List<Long> result = new ArrayList<Long>();
		result.addAll(l1.stream().filter(l -> !l2.contains(l))
				.collect(Collectors.toList()));
		result.addAll(l2.stream().filter(l -> !l1.contains(l))
				.collect(Collectors.toList()));
		return result;
	}

	public JavaRDD<EuclidVO> calDistance(Dataset<Row> movieDataDS) {
		JavaRDD<EuclidVO> euclidRdd = movieDataDS.javaRDD().map(row -> {
			EuclidVO evo = new EuclidVO();

			evo.setMovieId1(row.getInt(0));
			evo.setMovieId2(row.getInt(21));
			evo.setMovieTitle1(row.getString(1));
			evo.setMovieTitle2(row.getString(22));

			int start1 = 0;
			int start2 = 21;

			List<Double> elements = new ArrayList<Double>();

			for (int i = 2; i <= 20; i++) {
				if (i == 20) {
					double element = Math
							.abs(row.getDouble(start1 + i)
									- row.getDouble(start2 + i));
					elements.add(element);
					continue;
				}
				double element = Math
						.abs(row.getInt(start1 + i)
								- row.getInt(start2 + i));
				elements.add(element);
			}

			double pow = 0;
			for (double d : elements) {
				pow += d * d;
			}

			double euclid = Math.sqrt(pow);

			evo.setDistance(euclid);

			return evo;
		});

		return euclidRdd;
	}
}
