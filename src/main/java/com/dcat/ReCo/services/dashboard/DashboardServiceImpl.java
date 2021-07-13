package com.dcat.ReCo.services.dashboard;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dcat.ReCo.dtos.DistrictCountDTO;
import com.dcat.ReCo.dtos.MonthCount;
import com.dcat.ReCo.models.Dashboard;
import com.dcat.ReCo.repositories.CommentRepository;
import com.dcat.ReCo.repositories.ResourceRepository;

@Service
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	private EntityManager em;
	@Autowired
	private ResourceRepository resourceRepository;
	@Autowired
	private CommentRepository commentRepository;

	@Override
	public Dashboard getDashboard(String year) {
		Dashboard dashboard = new Dashboard();

		TypedQuery<Long> resQuery = em.createQuery("select count(r.id) from Restaurant r", Long.class);
		dashboard.setRestaurantCount(resQuery.getSingleResult());
		TypedQuery<Long> reserQuery = em.createQuery("select count(r.id) from Reservation r", Long.class);
		dashboard.setReservationCount(reserQuery.getSingleResult());
		TypedQuery<Long> userQuery = em.createQuery("select count(u.id) from User u where u.role.name='USER'",
				Long.class);
		dashboard.setUserCount(userQuery.getSingleResult());
		TypedQuery<Long> reviewQuery = em.createQuery("select count(r.id) from Review r", Long.class);
		dashboard.setReviewCount(reviewQuery.getSingleResult());

		dashboard.setTagAndRestaurants(resourceRepository.findAllTagWithAnalys());

		TypedQuery<DistrictCountDTO> disAndRes = em.createQuery(
				"select new com.dcat.ReCo.dtos.DistrictCountDTO(d.name , count(res.id)) from District d LEFT JOIN d.address ad JOIN ad.restaurant res GROUP BY d.id ORDER BY d.id",
				DistrictCountDTO.class);
		dashboard.setDistrictAndRestaurants(disAndRes.getResultList());

		TypedQuery<MonthCount> monthAndReQuery = em.createQuery(
				"select new com.dcat.ReCo.dtos.MonthCount(m.name, COUNT(r.id)) from MonthResource m LEFT JOIN Reservation r ON m.name = MONTH(r.timeComing) WHERE YEAR(r.timeComing) = :year GROUP BY m.name",
				MonthCount.class);
		monthAndReQuery.setParameter("year", Integer.parseInt(year));
		dashboard.setMonthAndReservations(monthAndReQuery.getResultList());

		TypedQuery<MonthCount> monthAndRevQuery = em.createQuery(
				"select new com.dcat.ReCo.dtos.MonthCount(m.name, COUNT(r.id)) from MonthResource m LEFT JOIN Review r ON m.name = MONTH(r.createdAt)  WHERE YEAR(r.createdAt) = :year GROUP BY m.name",
				MonthCount.class);
		monthAndRevQuery.setParameter("year", Integer.parseInt(year));
		dashboard.setMonthAndReviews(monthAndRevQuery.getResultList());
		dashboard.setComments(
				commentRepository.findBy(PageRequest.of(0, 3, Sort.by("createdAt").descending())).toList());
		return dashboard;
	}

	@Override
	public Dashboard getOnwerDashboard(String year) {
		// TODO get current login user
		Dashboard dashboard = new Dashboard();
		Long oid = 6L;
		TypedQuery<Long> resQuery = em.createQuery("select count(r.id) from Restaurant r WHERE r.owner.id =:oid",
				Long.class);
		resQuery.setParameter("oid", oid);
		dashboard.setRestaurantCount(resQuery.getSingleResult());
		TypedQuery<Long> reserQuery = em.createQuery(
				"select count(r.id) from Reservation r JOIN r.restaurant re WHERE re.owner.id =:oid", Long.class);
		reserQuery.setParameter("oid", oid);
		dashboard.setReservationCount(reserQuery.getSingleResult());
		TypedQuery<Long> reviewQuery = em.createQuery(
				"select count(r.id) from Review r JOIN r.restaurant re WHERE re.owner.id =:oid", Long.class);
		reviewQuery.setParameter("oid", oid);
		dashboard.setReviewCount(reviewQuery.getSingleResult());
		TypedQuery<MonthCount> monthAndReQuery = em.createQuery(
				"select new com.dcat.ReCo.dtos.MonthCount(m.name, COUNT(r.id)) from MonthResource m LEFT JOIN Reservation r ON m.name = MONTH(r.timeComing) JOIN r.restaurant res WHERE res.owner.id =:oid AND YEAR(r.timeComing) = :year GROUP BY m.name",
				MonthCount.class);
		monthAndReQuery.setParameter("oid", oid);
		monthAndReQuery.setParameter("year", Integer.parseInt(year));
		dashboard.setMonthAndReservations(monthAndReQuery.getResultList());

		TypedQuery<MonthCount> monthAndRevQuery = em.createQuery(
				"select new com.dcat.ReCo.dtos.MonthCount(m.name, COUNT(r.id)) from MonthResource m LEFT JOIN Review r ON m.name = MONTH(r.createdAt) JOIN r.restaurant res WHERE res.owner.id =:oid AND YEAR(r.createdAt) = :year GROUP BY m.name",
				MonthCount.class);
		monthAndRevQuery.setParameter("oid", oid);
		monthAndRevQuery.setParameter("year", Integer.parseInt(year));
		dashboard.setMonthAndReviews(monthAndRevQuery.getResultList());

		return dashboard;
	}
}
