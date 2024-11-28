package com.demo.assignment.service;

import com.demo.assignment.dto.ResponseDto;
import com.demo.assignment.dto.phim.ThongTinSuaPhimDto;
import com.demo.assignment.dto.phim.ThongTinTaoPhimDto;
import com.demo.assignment.entity.DanhGia;
import com.demo.assignment.entity.NguoiDung;
import com.demo.assignment.entity.Phim;
import com.demo.assignment.repository.DanhGiaRepository;
import com.demo.assignment.repository.PhimRepository;
import com.demo.assignment.security.CustomUserDetails;
import org.hibernate.cfg.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuanLyPhimServiceImpl implements QuanLyPhimService{
    private Logger logger = LoggerFactory.getLogger(QuanLyPhimServiceImpl.class);
    @Autowired
    private PhimRepository phimRepository;
    @Autowired
    private DanhGiaRepository danhGiaRepository;

    @Override
    public ResponseDto getPhimByNameAndGroup(String name, String group) {
        logger.info("Tim phim thuoc nhom " + group + " co ten " + name);
        ResponseDto responseDto = new ResponseDto();
        try {
            List<Phim> list = phimRepository.findByTenPhimContainingIgnoreCaseAndMaNhom(name, group);
            responseDto.setContent(list);
            responseDto.setStatusCode(200);
            responseDto.setMessage("Xử lý thành công!");
            responseDto.setDateTime(LocalDateTime.now().toString());
            responseDto.setMessageConstants(null);
            logger.info("Lay du lieu phim thanh cong");
        } catch (Exception e) {
            responseDto.setStatusCode(400);
            responseDto.setMessage("Không tìm thấy tài nguyên!");
            responseDto.setContent("");
            responseDto.setDateTime(LocalDateTime.now().toString());
            responseDto.setMessageConstants(null);
            logger.error("Lay du lieu phim that bai: " + e.getMessage());
        }
        return responseDto;
    }

    @Override
    public ResponseDto getPhimByPhimCode(int maPhim) {
        logger.info("Tim phim co ma phim " + maPhim);
        ResponseDto responseDto = new ResponseDto();
        try {
            Phim phim = phimRepository.findById(maPhim).get();
            responseDto.setContent(phim);
            responseDto.setStatusCode(200);
            responseDto.setMessage("Xử lý thành công!");
            responseDto.setDateTime(LocalDateTime.now().toString());
            responseDto.setMessageConstants(null);
            logger.info("Lay du lieu phim thanh cong");
        } catch (Exception e) {
            responseDto.setStatusCode(400);
            responseDto.setMessage("Không tìm thấy tài nguyên!");
            responseDto.setContent("Mã phim không hợp lệ");
            responseDto.setDateTime(LocalDateTime.now().toString());
            responseDto.setMessageConstants(null);
            logger.error("Lay du lieu phim that bai: " + e.getMessage());
        }
        return responseDto;
    }

    @Override
    public ResponseDto deletePhim(int maPhim) {
        logger.info("Xoa phim co ma phim " + maPhim);
        ResponseDto responseDto = new ResponseDto();
        try {
            responseDto.setStatusCode(200);
            responseDto.setMessage("Xóa phim thành công");
            responseDto.setContent("Xóa phim thành công");
            responseDto.setDateTime(LocalDateTime.now().toString());
            responseDto.setMessageConstants(null);
            phimRepository.deleteById(maPhim);
            logger.info("Xoa phim thanh cong");
        } catch (Exception e) {
            responseDto.setStatusCode(400);
            responseDto.setMessage("Xóa phim thất bại");
            responseDto.setContent("Phim đã có lịch chiếu");
            responseDto.setDateTime(LocalDateTime.now().toString());
            responseDto.setMessageConstants(null);
            logger.error("Xoa phim that bai: " + e.getMessage());
        }
        return responseDto;
    }

    @Override
    public ResponseDto addPhim(ThongTinTaoPhimDto thongTinTaoPhimDto) {
        ResponseDto responseDto = new ResponseDto();
        try {
            logger.info("Tao phim moi");
            if (thongTinTaoPhimDto.getHinhAnh() != null && !thongTinTaoPhimDto.getHinhAnh().isEmpty()) {
                String fileName = thongTinTaoPhimDto.getHinhAnh().getOriginalFilename();
                String newFileName = fileName.substring(0, fileName.lastIndexOf('.')) + ".jpg";
                String filePath = "D:\\assignment\\src\\main\\resources\\static\\" + newFileName;
                thongTinTaoPhimDto.getHinhAnh().transferTo(new File(filePath));
                logger.info("Luu anh vao bo nho");
                Phim phim = new Phim();
                phim.setTenPhim(thongTinTaoPhimDto.getTenPhim());
                String biDanh = Arrays.stream(thongTinTaoPhimDto.getTenPhim().split("\\s+"))
                        .map(String::toLowerCase).collect(Collectors.joining("-"));
                phim.setBiDanh(biDanh);
                phim.setTrailer(thongTinTaoPhimDto.getTrailer());
                phim.setHinhAnh("http://localhost:8080/hinh-anh/" + newFileName);
                phim.setMoTa(thongTinTaoPhimDto.getMoTa());
                phim.setMaNhom("GP01");
                phim.setNgayKhoiChieu(thongTinTaoPhimDto.getNgayKhoiChieu());
                phim.setHot(thongTinTaoPhimDto.isHot());
                phim.setDangChieu(thongTinTaoPhimDto.isDangChieu());
                phim.setSapChieu(thongTinTaoPhimDto.isSapChieu());
                phim = phimRepository.save(phim);
                logger.info("Them phim thanh cong voi id: " + phim.getMaPhim());
                responseDto.setContent("");
                responseDto.setStatusCode(200);
                responseDto.setMessage("Thêm phim thành công");
                responseDto.setDateTime(LocalDateTime.now().toString());
                responseDto.setMessageConstants(null);
            } else {
                responseDto.setStatusCode(500);
                responseDto.setMessage("Dữ liệu không hợp lệ");
                responseDto.setContent("");
                responseDto.setDateTime(LocalDateTime.now().toString());
                responseDto.setMessageConstants(null);
                logger.info("Them phim that bai");
            }
        } catch (Exception e){
            responseDto.setStatusCode(500);
            responseDto.setMessage("Dữ liệu không hợp lệ");
            responseDto.setContent("");
            responseDto.setDateTime(LocalDateTime.now().toString());
            responseDto.setMessageConstants(null);
            logger.error("Them phim that bai: " + e.getMessage());
        }
        return responseDto;
    }

    @Override
    public ResponseDto updatePhim(ThongTinSuaPhimDto thongTinSuaPhimDto) {
        ResponseDto responseDto = new ResponseDto();
        try {
            logger.info("Sua phim co id: " + thongTinSuaPhimDto.getMaPhim());
            if (thongTinSuaPhimDto.getHinhAnh() != null && !thongTinSuaPhimDto.getHinhAnh().isEmpty()) {
                logger.info("check first");
                String fileName = thongTinSuaPhimDto.getHinhAnh().getOriginalFilename();
                String newFileName = fileName.substring(0, fileName.lastIndexOf('.')) + ".jpg";
                String filePath = "D:\\assignment\\src\\main\\resources\\static\\" + newFileName;
                thongTinSuaPhimDto.getHinhAnh().transferTo(new File(filePath));
                logger.info("Luu anh vao bo nho");
                Phim phim = new Phim();
                phim.setMaPhim(thongTinSuaPhimDto.getMaPhim());
                phim.setTenPhim(thongTinSuaPhimDto.getTenPhim());
                String biDanh = Arrays.stream(thongTinSuaPhimDto.getTenPhim().split("\\s+"))
                        .map(String::toLowerCase).collect(Collectors.joining("-"));
                phim.setBiDanh(biDanh);
                phim.setTrailer(thongTinSuaPhimDto.getTrailer());
                phim.setHinhAnh("http://localhost:8080/hinh-anh/" + newFileName);
                phim.setMoTa(thongTinSuaPhimDto.getMoTa());
                phim.setMaNhom("GP01");
                phim.setNgayKhoiChieu(thongTinSuaPhimDto.getNgayKhoiChieu());
                phim.setHot(thongTinSuaPhimDto.isHot());
                phim.setDangChieu(thongTinSuaPhimDto.isDangChieu());
                phim.setSapChieu(thongTinSuaPhimDto.isSapChieu());
                phim = phimRepository.save(phim);
                logger.info("Sua phim thanh cong voi id: " + phim.getMaPhim());
                responseDto.setContent("");
                responseDto.setStatusCode(200);
                responseDto.setMessage("Sửa phim thành công");
                responseDto.setDateTime(LocalDateTime.now().toString());
                responseDto.setMessageConstants(null);
            } else {
                responseDto.setStatusCode(500);
                responseDto.setMessage("Dữ liệu không hợp lệ");
                responseDto.setContent("");
                responseDto.setDateTime(LocalDateTime.now().toString());
                responseDto.setMessageConstants(null);
                logger.info("Sua phim that bai");
            }
        } catch (Exception e){
            responseDto.setStatusCode(500);
            responseDto.setMessage("Dữ liệu không hợp lệ");
            responseDto.setContent("");
            responseDto.setDateTime(LocalDateTime.now().toString());
            responseDto.setMessageConstants(null);
            logger.error("Sua phim that bai: " + e.getMessage());
        }
        return responseDto;
    }

    @Override
    public ResponseDto addRating(int maPhim, DanhGia danhGia) {
        logger.info("Them danh gia cho phim co ma phim {}", maPhim);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        NguoiDung nguoiDung = customUserDetails.getNguoiDung();
        danhGia.setNguoiBinhLuan(nguoiDung.getHoTen());
        Phim phim = phimRepository.findById(maPhim).get();
        danhGia.setPhim(phim);
        if(!phim.getDanhGias().isEmpty()){
            List<DanhGia> danhGias = phim.getDanhGias();
            danhGias.add(danhGia);
            phim.setDanhGias(danhGias);
            double averageRating = danhGias.stream()
                    .mapToDouble(DanhGia::getSoSao)
                    .average()
                    .orElse(0.0);
            phim.setDanhGia(averageRating);
        } else {
            List<DanhGia> danhGias = new ArrayList<DanhGia>();
            danhGias.add(danhGia);
            phim.setDanhGias(danhGias);
            double averageRating = danhGias.stream()
                    .mapToDouble(DanhGia::getSoSao)
                    .average()
                    .orElse(0.0);
            phim.setDanhGia(averageRating);
        }
        danhGiaRepository.save(danhGia);
        phimRepository.save(phim);
        logger.info("Them danh gia thanh cong");
        return null;
    }

    @Override
    public List<DanhGia> getRating(int maPhim) {
        logger.info("Lay danh sach danh gia cua phim co ma phim {}", maPhim);
        Phim phim = phimRepository.findById(maPhim).get();
        return phim.getDanhGias();
    }


}
