package com.demo.assignment.service;

import com.demo.assignment.dto.ResponseDto;
import com.demo.assignment.dto.danhSachPhongVe.PhongVeDto;
import com.demo.assignment.dto.danhSachPhongVe.ThongTinGheTheoPhongVeDto;
import com.demo.assignment.dto.danhSachPhongVe.ThongTinPhimTheoLichChieuDto;
import com.demo.assignment.dto.lichChieu.LichChieuDto;
import com.demo.assignment.dto.ve.GheDto;
import com.demo.assignment.dto.ve.VeDto;
import com.demo.assignment.entity.*;
import com.demo.assignment.exception.AppException;
import com.demo.assignment.exception.ErrorCode;
import com.demo.assignment.repository.*;
import com.demo.assignment.security.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuanLyVeServiceImpl implements QuanLyVeService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private VeRepository veRepository;
    @Autowired
    private RapRepository rapRepository;
    @Autowired
    private PhimRepository phimRepository;
    @Autowired
    private LichChieuRepository lichChieuRepository;
    @Autowired
    private GheRepository gheRepository;
    @Override
    public ResponseDto themLichChieu(LichChieuDto lichChieuDto) {
        logger.info("Du lieu nhan vao: " + lichChieuDto);
        LocalDateTime newStartTime = LocalDateTime.parse(lichChieuDto.getNgayChieuGioChieu(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        LocalDateTime newEndTime = LocalDateTime.parse(lichChieuDto.getNgayChieuGioChieu(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")).plusMinutes(120);
        int maRap = Integer.parseInt(lichChieuDto.getMaRap());
        if (lichChieuRepository.existsOverlappingSchedules(maRap, newStartTime, newEndTime)) {
            throw new AppException(ErrorCode.CANNOT_CREATE_SCHEDULE);
        }
        Rap rap = rapRepository.findById(maRap).get();
        List<LichChieu> lichChieuListTheoRap = rap.getLichChieus();
        LichChieu lichChieu = new LichChieu();
        lichChieu.setNgayChieuGioChieu(lichChieuDto.getNgayChieuGioChieu());
        lichChieu.setGiaVe(lichChieuDto.getGiaVe());
        lichChieu.setStartTime(newStartTime);
        lichChieu.setEndTime(newEndTime);
        lichChieuListTheoRap.add(lichChieu);
        rap.setLichChieus(lichChieuListTheoRap);
        lichChieu.setRap(rap);
        Phim phim = phimRepository.findById(lichChieuDto.getMaPhim()).get();
        List<LichChieu> lichChieuList = phim.getLichChieus();
        lichChieuList.add(lichChieu);
        phim.setLichChieus(lichChieuList);
        lichChieu.setPhim(phim);
        PhongVe phongVe = new PhongVe();
        phongVe.setLichChieu(lichChieu);
        lichChieu.setPhongVe(phongVe);
        lichChieu = this.lichChieuRepository.save(lichChieu);
        logger.info("Da them lich chieu vao csdl: " + lichChieu);
        return ResponseDto.builder()
                .statusCode(200)
                .message("Xử lý thành công!")
                .content("Tạo lịch chiếu thành công")
                .dateTime(LocalDateTime.now().toString())
                .build();
    }
    @Override
    public ResponseDto datVe(VeDto veDto) {
        logger.info("Thong tin dat ve: " + veDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        NguoiDung nguoiDung = customUserDetails.getNguoiDung();
        logger.info("Nguoi dung: " + nguoiDung.getTaiKhoan());
        Ve ve = new Ve();
        ve.setNgayDat(LocalDateTime.now().toString());

        List<GheDto> gheDtos = veDto.getDanhSachVe();
        List<Ghe> gheList = new ArrayList<>();
        for (GheDto gheDto : gheDtos) {
            Ghe ghe = gheRepository.findById(gheDto.getMaGhe()).get();
            ghe.setTaiKhoanNguoiDat(nguoiDung.getTaiKhoan());
            ghe.setDaDat(true);
            ghe.setVe(ve);
            gheList.add(ghe);
        }
        ve.setGhes(gheList);
        LichChieu lichChieu = lichChieuRepository.findById(veDto.getMaLichChieu()).get();
        List<Ve> veList = lichChieu.getVes();
        ve.setLichChieu(lichChieu);
        veList.add(ve);
        lichChieu.setVes(veList);

        ve.setNguoiDung(nguoiDung);
        veList = nguoiDung.getVes();
        veList.add(ve);
        nguoiDung.setVes(veList);

        veRepository.save(ve);
        logger.info("Dat ve thanh cong");
        return ResponseDto.builder()
                .statusCode(200)
                .message("Xử lý thành công!")
                .content("Đặt vé thành công")
                .dateTime(LocalDateTime.now().toString())
                .build();
    }

    @Override
    public ResponseDto layDanhSachPhongVe(int maLichChieu) {
        logger.info("Lay thong tin phong ve thao ma lich chieu: " + maLichChieu);
        PhongVeDto phongVeDto = null;
        try {
            LichChieu lichChieu = lichChieuRepository.findById(maLichChieu).get();
            Rap rap = lichChieu.getRap();
            Phim phim = lichChieu.getPhim();
            phongVeDto = new PhongVeDto();
            ThongTinPhimTheoLichChieuDto thongTinPhimTheoLichChieuDto = new ThongTinPhimTheoLichChieuDto();
            thongTinPhimTheoLichChieuDto.setMaLichChieu(maLichChieu);
            thongTinPhimTheoLichChieuDto.setTenCumRap(rap.getCumRap().getTenCumRap());
            thongTinPhimTheoLichChieuDto.setTenRap(rap.getTenRap());
            thongTinPhimTheoLichChieuDto.setDiaChi(rap.getCumRap().getDiaChi());
            thongTinPhimTheoLichChieuDto.setTenPhim(phim.getTenPhim());
            thongTinPhimTheoLichChieuDto.setHinhAnh(phim.getHinhAnh());
            thongTinPhimTheoLichChieuDto.setNgayChieu(lichChieu.getNgayChieuGioChieu().substring(0, 10));
            thongTinPhimTheoLichChieuDto.setGioChieu(lichChieu.getNgayChieuGioChieu().substring(11, 16));
            phongVeDto.setThongTinPhim(thongTinPhimTheoLichChieuDto);
            List<ThongTinGheTheoPhongVeDto> danhSachGhe = new ArrayList<>();
            List<Ghe> gheList = lichChieu.getPhongVe().getDanhSachGhe();
            for (Ghe ghe : gheList) {
                ThongTinGheTheoPhongVeDto thongTinGheTheoPhongVeDto = new ThongTinGheTheoPhongVeDto();
                thongTinGheTheoPhongVeDto.setMaGhe(ghe.getMaGhe());
                thongTinGheTheoPhongVeDto.setTenGhe(ghe.getTenGhe());
                thongTinGheTheoPhongVeDto.setMaRap(rap.getMaRap());
                thongTinGheTheoPhongVeDto.setLoaiGhe(ghe.getLoaiGhe());
                thongTinGheTheoPhongVeDto.setStt(ghe.getStt());
                thongTinGheTheoPhongVeDto.setGiaVe(lichChieu.getGiaVe());
                if (ghe.getLoaiGhe().equals("Vip")) thongTinGheTheoPhongVeDto.setGiaVe(lichChieu.getGiaVe()*1.2);
                thongTinGheTheoPhongVeDto.setDaDat(ghe.isDaDat());
                thongTinGheTheoPhongVeDto.setTaiKhoanNguoiDat(ghe.getTaiKhoanNguoiDat());
                danhSachGhe.add(thongTinGheTheoPhongVeDto);
            }
            phongVeDto.setDanhSachGhe(danhSachGhe);
            logger.info("Lay thong tin phong ve thanh cong");
            return ResponseDto.builder()
                    .statusCode(200)
                    .message("Xử lý thành công!")
                    .content(phongVeDto)
                    .dateTime(LocalDateTime.now().toString())
                    .build();
        } catch (Exception e) {
            logger.info("Lay danh sach phong ve that bai");
            logger.error(e.getMessage());
            throw new AppException(ErrorCode.NOT_FOUND);
        }
    }

}
