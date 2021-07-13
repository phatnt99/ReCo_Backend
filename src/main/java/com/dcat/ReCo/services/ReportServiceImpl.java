package com.dcat.ReCo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dcat.ReCo.dtos.ReportCreateDTO;
import com.dcat.ReCo.models.Report;
import com.dcat.ReCo.models.Restaurant;
import com.dcat.ReCo.models.Review;
import com.dcat.ReCo.models.User;
import com.dcat.ReCo.models.Voucher;
import com.dcat.ReCo.repositories.ReportRepository;
import com.dcat.ReCo.repositories.RestaurantRepository;
import com.dcat.ReCo.repositories.ReviewRepository;
import com.dcat.ReCo.repositories.UserRepository;
import com.dcat.ReCo.repositories.VoucherRepository;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ReportRepository reportRepository;

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private VoucherRepository voucherRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Page<Report> getAll(Pageable pageable) {
		return reportRepository.findAll(pageable).map(report -> {
			if (report.getType() == 1) {
				// restaurant
				Restaurant r = restaurantRepository.findById(report.getReportableId()).orElse(null);
				if (r != null) {
					report.setReportableName(r.getName());
				}

			}
			if (report.getType() == 2) {
				// review
				Review r = reviewRepository.findById(report.getReportableId()).orElse(null);
				if (r != null) {
					report.setReportableName(r.getTitle());
				}
			}
			if (report.getType() == 3) {
				// voucher
				Voucher v = voucherRepository.findById(report.getReportableId()).orElse(null);
				if (v != null) {
					report.setReportableName(v.getTitle());
				}
			}
			if (report.getType() == 5) {
				// voucher
				User u = userRepository.findById(report.getReportableId()).orElse(null);
				if (u != null) {
					report.setReportableName(u.getFullName());
				}
			}

			return report;
		});
	}

	@Override
	public Page<Report> search(String query, Pageable pageable) {
		return reportRepository.findAllForSearch(query, pageable).map(report -> {
			if (report.getType() == 1) {
				// restaurant
				Restaurant r = restaurantRepository.findById(report.getReportableId()).orElse(null);
				if (r != null) {
					report.setReportableName(r.getName());
				}

			}
			if (report.getType() == 2) {
				// review
				Review r = reviewRepository.findById(report.getReportableId()).orElse(null);
				if (r != null) {
					report.setReportableName(r.getTitle());
				}
			}
			if (report.getType() == 3) {
				// voucher
				Voucher v = voucherRepository.findById(report.getReportableId()).orElse(null);
				if (v != null) {
					report.setReportableName(v.getTitle());
				}
			}
			if (report.getType() == 5) {
				// voucher
				User u = userRepository.findById(report.getReportableId()).orElse(null);
				if (u != null) {
					report.setReportableName(u.getFullName());
				}
			}

			return report;
		});
	}

	@Override
	public Page<Report> getByOwner(Long ownerId, Pageable pageable) {

		return reportRepository.findByRestaurantOwnerIdAndTypeNot(ownerId, 5, pageable).map(report -> {
			if (report.getType() == 1) {
				// restaurant
				Restaurant r = restaurantRepository.findById(report.getReportableId()).orElse(null);
				if (r != null) {
					report.setReportableName(r.getName());
				}

			}
			if (report.getType() == 2) {
				// review
				Review r = reviewRepository.findById(report.getReportableId()).orElse(null);
				if (r != null) {
					report.setReportableName(r.getTitle());
				}
			}
			if (report.getType() == 3) {
				// voucher
				Voucher v = voucherRepository.findById(report.getReportableId()).orElse(null);
				if (v != null) {
					report.setReportableName(v.getTitle());
				}
			}

			return report;
		});
	}

	@Override
	public Report createOne(ReportCreateDTO dto) {
		Report r = new Report();
		r.setContent(dto.getContent());
		r.setReportableId(dto.getReportableId());
		r.setType(dto.getType());

		if (r.getType() == 1) {
			// restaurant
			r.setRestaurant(restaurantRepository.findById(r.getReportableId()).orElse(null));

		}
		if (r.getType() == 2) {
			// review
			Review rv = reviewRepository.findById(r.getReportableId()).orElse(null);
			if (rv != null) {
				r.setRestaurant(rv.getRestaurant());
			}
		}
		if (r.getType() == 3) {
			// voucher
			Voucher v = voucherRepository.findById(r.getReportableId()).orElse(null);
			if (v != null) {
				r.setRestaurant(v.getRestaurant());
			}
		}

		return reportRepository.save(r);
	}
}
