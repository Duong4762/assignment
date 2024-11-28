package com.demo.assignment.service;

import com.demo.assignment.dto.NguoiDung.NguoiDungDto;
import com.demo.assignment.dto.NguoiDung.ResponseThongTinNguoiDungDto;
import com.demo.assignment.dto.ResponseDto;
import com.demo.assignment.dto.thongTinDangNhap.ResponseDangNhap;
import com.demo.assignment.dto.thongTinDangNhap.ThongTinDangNhap;
import com.demo.assignment.dto.ve.GheTheoThongTinNguoiDungDto;
import com.demo.assignment.dto.ve.VeTheoThongTinNguoiDungDto;
import com.demo.assignment.entity.Ghe;
import com.demo.assignment.entity.NguoiDung;
import com.demo.assignment.entity.Ve;
import com.demo.assignment.repository.NguoiDungRepository;
import com.demo.assignment.security.CustomUserDetails;
import com.demo.assignment.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuanLyNguoiDungServiceImpl implements QuanLyNguoiDungService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseDto dangKy(NguoiDungDto nguoiDungDto) {
        NguoiDung nguoiDungExist = nguoiDungRepository.findByTaiKhoan(nguoiDungDto.getTaiKhoan());
        if (nguoiDungExist == null) {
            logger.info("Them nguoi dung moi: " + nguoiDungDto);
            NguoiDung nguoiDung = new NguoiDung();
            nguoiDung.setTaiKhoan(nguoiDungDto.getTaiKhoan());
            nguoiDung.setMatKhau(passwordEncoder.encode(nguoiDungDto.getMatKhau()));
            nguoiDung.setEmail(nguoiDungDto.getEmail());
            nguoiDung.setSoDT(nguoiDungDto.getSoDt());
            nguoiDung.setMaNhom(nguoiDungDto.getMaNhom());
            nguoiDung.setLoaiNguoiDung("khach hang");
            nguoiDung.setHoTen(nguoiDungDto.getHoTen());
            nguoiDungRepository.save(nguoiDung);
            logger.info("Them nguoi dung thanh cong");
            ResponseDto responseDto = new ResponseDto();
            responseDto.setStatusCode(200);
            responseDto.setMessage("Xử lý thành công!");
            responseDto.setContent("Đăng ký thành công");
            responseDto.setDateTime(LocalDateTime.now().toString());
            responseDto.setMessageConstants(null);
            return responseDto;
        } else{
            ResponseDto responseDto = new ResponseDto();
            responseDto.setStatusCode(400);
            responseDto.setMessage("Thất bại");
            responseDto.setContent("Tài khoản đã tồn tại");
            responseDto.setDateTime(LocalDateTime.now().toString());
            responseDto.setMessageConstants(null);
            return responseDto;
        }
    }

    @Override
    public ResponseDto dangNhap(ThongTinDangNhap thongTinDangNhap) {
        ResponseDto responseDto = null;
        try {
            logger.info("Dang nhap nguoi dung voi thong tin: " + thongTinDangNhap);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            thongTinDangNhap.getTaiKhoan(),
                            thongTinDangNhap.getMatKhau()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            NguoiDung nguoiDung = ((CustomUserDetails) authentication.getPrincipal()).getNguoiDung();
            String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
            ResponseDangNhap responseDangNhap = new ResponseDangNhap();
            responseDangNhap.setTaikhoan(nguoiDung.getTaiKhoan());
            responseDangNhap.setHoTen(nguoiDung.getHoTen());
            responseDangNhap.setEmail(nguoiDung.getEmail());
            responseDangNhap.setSoDT(nguoiDung.getSoDT());
            responseDangNhap.setMaNhom(nguoiDung.getMaNhom());
            responseDangNhap.setMaLoaiNguoiDung(nguoiDung.getLoaiNguoiDung());
            responseDangNhap.setAccessToken(jwt);
            responseDto = new ResponseDto();
            responseDto.setStatusCode(200);
            responseDto.setMessage("Xử lý thành công!");
            responseDto.setContent(responseDangNhap);
            responseDto.setDateTime(LocalDateTime.now().toString());
            responseDto.setMessageConstants(null);
            logger.info("Dang nhap thanh cong");
        } catch (AuthenticationException e) {
            logger.error(e.getMessage());
            logger.info("Dang nhap that bai");
            responseDto = new ResponseDto();
            responseDto.setStatusCode(404);
            responseDto.setMessage("Không tìm thấy tài nguyên");
            responseDto.setContent("Tài khoản hoặc mật khẩu không đúng!");
            responseDto.setDateTime(LocalDateTime.now().toString());
            responseDto.setMessageConstants(null);
        }
        return responseDto;
    }

    @Override
    public ResponseDto layThongTinTaiKhoan() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        NguoiDung nguoiDung = customUserDetails.getNguoiDung();
        logger.info("Lay thong tin tai khoan: " + nguoiDung.getTaiKhoan());
        ResponseThongTinNguoiDungDto responseThongTinNguoiDungDto = new ResponseThongTinNguoiDungDto();
        responseThongTinNguoiDungDto.setTaiKhoan(nguoiDung.getTaiKhoan());
        responseThongTinNguoiDungDto.setHoTen(nguoiDung.getHoTen());
        responseThongTinNguoiDungDto.setEmail(nguoiDung.getEmail());
        responseThongTinNguoiDungDto.setSoDT(nguoiDung.getSoDT());
        responseThongTinNguoiDungDto.setMaNhom(nguoiDung.getMaNhom());
        responseThongTinNguoiDungDto.setLoaiNguoiDung(nguoiDung.getLoaiNguoiDung());
        List<Ve> ves = nguoiDung.getVes();
        List<VeTheoThongTinNguoiDungDto> veTheoThongTinNguoiDungDtos = new ArrayList<>();
        for(Ve ve : ves) {
            List<GheTheoThongTinNguoiDungDto> gheTheoThongTinNguoiDungDtos = new ArrayList<>();
            for(Ghe ghe: ve.getGhes()){
                GheTheoThongTinNguoiDungDto gheTheoThongTinNguoiDungDto = new GheTheoThongTinNguoiDungDto();
                gheTheoThongTinNguoiDungDto.setMaHeThongRap(ghe.getPhongVe().getLichChieu().getRap().getCumRap().getHeThongRap().getMaHeThongRap());
                gheTheoThongTinNguoiDungDto.setTenHeThongRap(ghe.getPhongVe().getLichChieu().getRap().getCumRap().getTenCumRap());
                gheTheoThongTinNguoiDungDto.setMaCumRap(ghe.getPhongVe().getLichChieu().getRap().getTenRap());
                gheTheoThongTinNguoiDungDto.setTenCumRap(ghe.getPhongVe().getLichChieu().getRap().getTenRap());
                gheTheoThongTinNguoiDungDto.setMaRap(ghe.getPhongVe().getLichChieu().getRap().getMaRap());
                gheTheoThongTinNguoiDungDto.setTenRap(ghe.getPhongVe().getLichChieu().getRap().getTenRap());
                gheTheoThongTinNguoiDungDto.setMaGhe(ghe.getMaGhe());
                gheTheoThongTinNguoiDungDto.setTenGhe(ghe.getTenGhe());
                gheTheoThongTinNguoiDungDtos.add(gheTheoThongTinNguoiDungDto);
            }
            VeTheoThongTinNguoiDungDto veTheoThongTinNguoiDungDto = new VeTheoThongTinNguoiDungDto();
            veTheoThongTinNguoiDungDto.setDanhSachGhe(gheTheoThongTinNguoiDungDtos);
            veTheoThongTinNguoiDungDto.setMaVe(ve.getId());
            veTheoThongTinNguoiDungDto.setNgayDat(ve.getNgayDat());
            veTheoThongTinNguoiDungDto.setTenPhim(ve.getLichChieu().getPhim().getTenPhim());
            veTheoThongTinNguoiDungDto.setHinhAnh(ve.getLichChieu().getPhim().getHinhAnh());
            veTheoThongTinNguoiDungDto.setGiaVe(ve.getLichChieu().getGiaVe());
            veTheoThongTinNguoiDungDto.setThoiLuongPhim(120);
            veTheoThongTinNguoiDungDtos.add(veTheoThongTinNguoiDungDto);
        }
        responseThongTinNguoiDungDto.setThongTinDatVe(veTheoThongTinNguoiDungDtos);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatusCode(200);
        responseDto.setMessage("Xử lý thành công!");
        responseDto.setContent(responseThongTinNguoiDungDto);
        responseDto.setDateTime(LocalDateTime.now().toString());
        responseDto.setMessageConstants(null);
        logger.info("Lay thong tin nguoi dung thanh cong");
        return responseDto;
    }
}
