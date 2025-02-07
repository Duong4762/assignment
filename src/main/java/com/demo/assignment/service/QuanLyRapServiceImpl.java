package com.demo.assignment.service;

import com.demo.assignment.dto.ResponseDto;
import com.demo.assignment.dto.thongTinLichChieuHeThongRap.ThongTinLichChieuCumRapDto;
import com.demo.assignment.dto.thongTinLichChieuHeThongRap.ThongTinLichChieuHeThongRapDto;
import com.demo.assignment.dto.thongTinLichChieuHeThongRap.ThongTinLichChieuTheoPhimDto;
import com.demo.assignment.dto.thongTinLichChieuHeThongRap.ThongTinPhimDto;
import com.demo.assignment.dto.thongTinLichChieuPhim.CumRapChieuDto;
import com.demo.assignment.dto.thongTinLichChieuPhim.HeThongRapChieuDto;
import com.demo.assignment.dto.thongTinLichChieuPhim.LichChieuPhimDto;
import com.demo.assignment.dto.thongTinLichChieuPhim.ThongTinLichChieuPhimDto;
import com.demo.assignment.dto.thongTinLichChieuTheoCumRap.ThongTinLichChieu;
import com.demo.assignment.dto.thongTinLichChieuTheoCumRap.ThongTinPhim;
import com.demo.assignment.dto.thongTinLichChieuTheoCumRap.ThongTinRap;
import com.demo.assignment.entity.*;
import com.demo.assignment.exception.AppException;
import com.demo.assignment.exception.ErrorCode;
import com.demo.assignment.repository.CumRapRepository;
import com.demo.assignment.repository.HeThongRapRepository;
import com.demo.assignment.repository.LichChieuRepository;
import com.demo.assignment.repository.PhimRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuanLyRapServiceImpl implements QuanLyRapService {
    private Logger logger = LoggerFactory.getLogger(QuanLyRapServiceImpl.class);
    @Autowired
    private LichChieuRepository lichChieuRepository;
    @Autowired
    private HeThongRapRepository heThongRapRepository;
    @Autowired
    private PhimRepository phimRepository;
    @Autowired
    private CumRapRepository cumRapRepository;
    @Override
    public ResponseDto layThongTinCumRapTheoHeThong(String maHeThongRap) {
        logger.info("Lay thong tin cum rap theo ma he thong rap: " + maHeThongRap);
        try {
            HeThongRap heThongRap = heThongRapRepository.findByMaHeThongRap(maHeThongRap);
            logger.info("Lay thong tin cum rap thanh cong");
            return ResponseDto.builder()
                    .statusCode(200)
                    .message("Xử lý thành công!")
                    .content(heThongRap.getCumRaps())
                    .dateTime(LocalDateTime.now().toString())
                    .build();
        } catch (Exception e) {
            logger.error("Lay thong tin cum rap that bai: " + e.getMessage());
            throw new AppException(ErrorCode.NOT_FOUND);
        }
    }

    @Override
    public ResponseDto layThongTinLichChieuPhim(int maPhim) {
        logger.info("Lay thong tin lich chieu phim theo ma phim: " + maPhim);
        try {
            Phim phim = phimRepository.findById(maPhim).get();
            logger.info("Phim tim duoc: " + phim.getTenPhim());
            ThongTinLichChieuPhimDto thongTinLichChieuPhimDto = new ThongTinLichChieuPhimDto();
            thongTinLichChieuPhimDto.setMaPhim(maPhim);
            thongTinLichChieuPhimDto.setTenPhim(phim.getTenPhim());
            thongTinLichChieuPhimDto.setBiDanh(phim.getBiDanh());
            thongTinLichChieuPhimDto.setTrailer(phim.getTrailer());
            thongTinLichChieuPhimDto.setHinhAnh(phim.getHinhAnh());
            thongTinLichChieuPhimDto.setMoTa(phim.getMoTa());
            thongTinLichChieuPhimDto.setMaNhom(phim.getMaNhom());
            thongTinLichChieuPhimDto.setHot(phim.isHot());
            thongTinLichChieuPhimDto.setDangChieu(phim.isDangChieu());
            thongTinLichChieuPhimDto.setSapChieu(phim.isSapChieu());
            thongTinLichChieuPhimDto.setDanhGia(phim.getDanhGia());
            thongTinLichChieuPhimDto.setNgayKhoiChieu(phim.getNgayKhoiChieu());
            List<LichChieu> lichChieuList = phim.getLichChieus();
            Map<Integer, Map<Integer, List<LichChieuPhimDto>>> mapHeThongRapChieu = new HashMap<>();
            for (LichChieu lichChieu : lichChieuList) {
                int maHeThongRapChieu = lichChieu.getRap().getCumRap().getHeThongRap().getId();
                int maCumRapChieu = lichChieu.getRap().getCumRap().getId();
                if(mapHeThongRapChieu.containsKey(maHeThongRapChieu)){
                    Map<Integer, List<LichChieuPhimDto>> mapCumRapChieu = mapHeThongRapChieu.get(maHeThongRapChieu);
                    if (mapCumRapChieu.containsKey(maCumRapChieu)){
                        List<LichChieuPhimDto> lichChieuPhimDtoList = mapCumRapChieu.get(maCumRapChieu);
                        LichChieuPhimDto lichChieuPhimDto = new LichChieuPhimDto();
                        lichChieuPhimDto.setMaLichChieu(Integer.toString(lichChieu.getMaLichChieu()));
                        lichChieuPhimDto.setMaRap(Integer.toString(lichChieu.getRap().getMaRap()));
                        lichChieuPhimDto.setTenRap(lichChieu.getRap().getTenRap());
                        LocalDateTime ngayChieuGioChieu = LocalDateTime.parse(lichChieu.getNgayChieuGioChieu(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                        lichChieuPhimDto.setNgayChieuGioChieu(ngayChieuGioChieu.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        lichChieuPhimDto.setGiaVe(lichChieu.getGiaVe());
                        lichChieuPhimDto.setThoiLuong(120);
                        lichChieuPhimDtoList.add(lichChieuPhimDto);
                        mapCumRapChieu.put(maCumRapChieu, lichChieuPhimDtoList);
                    } else{
                        List<LichChieuPhimDto> lichChieuPhimDtoList = new ArrayList<>();
                        LichChieuPhimDto lichChieuPhimDto = new LichChieuPhimDto();
                        lichChieuPhimDto.setMaLichChieu(Integer.toString(lichChieu.getMaLichChieu()));
                        lichChieuPhimDto.setMaRap(Integer.toString(lichChieu.getRap().getMaRap()));
                        lichChieuPhimDto.setTenRap(lichChieu.getRap().getTenRap());
                        LocalDateTime ngayChieuGioChieu = LocalDateTime.parse(lichChieu.getNgayChieuGioChieu(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                        lichChieuPhimDto.setNgayChieuGioChieu(ngayChieuGioChieu.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        lichChieuPhimDto.setGiaVe(lichChieu.getGiaVe());
                        lichChieuPhimDto.setThoiLuong(120);
                        lichChieuPhimDtoList.add(lichChieuPhimDto);
                        mapCumRapChieu.put(maCumRapChieu, lichChieuPhimDtoList);
                    }
                    mapHeThongRapChieu.put(maHeThongRapChieu, mapCumRapChieu);
                } else{
                    Map<Integer, List<LichChieuPhimDto>> mapCumRapChieu = new HashMap<>();
                    List<LichChieuPhimDto> lichChieuPhimDtoList = new ArrayList<>();
                    LichChieuPhimDto lichChieuPhimDto = new LichChieuPhimDto();
                    lichChieuPhimDto.setMaLichChieu(Integer.toString(lichChieu.getMaLichChieu()));
                    lichChieuPhimDto.setMaRap(Integer.toString(lichChieu.getRap().getMaRap()));
                    lichChieuPhimDto.setTenRap(lichChieu.getRap().getTenRap());
                    LocalDateTime ngayChieuGioChieu = LocalDateTime.parse(lichChieu.getNgayChieuGioChieu(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                    lichChieuPhimDto.setNgayChieuGioChieu(ngayChieuGioChieu.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    lichChieuPhimDto.setGiaVe(lichChieu.getGiaVe());
                    lichChieuPhimDto.setThoiLuong(120);
                    lichChieuPhimDtoList.add(lichChieuPhimDto);
                    mapCumRapChieu.put(maCumRapChieu, lichChieuPhimDtoList);
                    mapHeThongRapChieu.put(maHeThongRapChieu, mapCumRapChieu);
                }
            }
            List<HeThongRapChieuDto> heThongRapChieu = new ArrayList<>();
            for(int maHeThongRapChieu : mapHeThongRapChieu.keySet()){
                List<CumRapChieuDto> cumRapChieu = new ArrayList<>();
                for (int idCumRapChieu : mapHeThongRapChieu.get(maHeThongRapChieu).keySet()){
                    List<LichChieuPhimDto> lichChieuPhimDtoList = new ArrayList<>();
                    lichChieuPhimDtoList = mapHeThongRapChieu.get(maHeThongRapChieu).get(idCumRapChieu);
                    CumRapChieuDto cumRapChieuDto = new CumRapChieuDto();
                    cumRapChieuDto.setLichChieuPhim(lichChieuPhimDtoList);
                    CumRap cumRap = cumRapRepository.findById(idCumRapChieu).get();
                    cumRapChieuDto.setMaCumRap(cumRap.getMaCumRap());
                    cumRapChieuDto.setTenCumRap(cumRap.getTenCumRap());
                    cumRapChieuDto.setHinhAnh("https://s3img.vcdn.vn/123phim/2021/01/bhd-star-bitexco-16105952137769.png");
                    cumRapChieuDto.setDiaChi(cumRap.getDiaChi());
                    cumRapChieu.add(cumRapChieuDto);
                }
                HeThongRap heThongRap = heThongRapRepository.findById(maHeThongRapChieu).get();
                HeThongRapChieuDto heThongRapChieuDto = new HeThongRapChieuDto();
                heThongRapChieuDto.setCumRapChieu(cumRapChieu);
                heThongRapChieuDto.setMaHeThongRap(heThongRap.getMaHeThongRap());
                heThongRapChieuDto.setTenHeThongRap(heThongRap.getTenHeThongRap());
                heThongRapChieuDto.setLogo(heThongRap.getLogo());
                heThongRapChieu.add(heThongRapChieuDto);
            }
            thongTinLichChieuPhimDto.setHeThongRapChieu(heThongRapChieu);
            logger.info("Lay thong tin lich chieu phim thanh cong");
            return ResponseDto.builder()
                    .statusCode(200)
                    .message("Xử lý thành công!")
                    .content(thongTinLichChieuPhimDto)
                    .dateTime(LocalDateTime.now().toString())
                    .build();
        } catch (Exception e) {
            logger.info("Lay thong tin lich chieu phim that bai");
            logger.error(e.getMessage());
            throw new AppException(ErrorCode.NOT_FOUND);
        }
    }

    @Override
    public ResponseDto layThongTinLichChieuHeThongRap(String maNhom) {
        logger.info("Lay thong tin lich chieu he thong rap theo ma nhom: " + maNhom);
        try {
            List<LichChieu> lichChieuList = this.lichChieuRepository.findByPhim_MaNhom(maNhom);
            logger.info("Tim duoc " + lichChieuList.size() + " lich chieu");
            Map<Integer, Map<Integer, Map<Integer, List<ThongTinLichChieuTheoPhimDto>>>> mapThongTinLichChieuHeThongRap = new HashMap<>();
            for (LichChieu lichChieu : lichChieuList) {
                Rap rap = lichChieu.getRap();
                CumRap cumRap = rap.getCumRap();
                HeThongRap heThongRap = cumRap.getHeThongRap();
                Phim phim = lichChieu.getPhim();
                if(mapThongTinLichChieuHeThongRap.containsKey(heThongRap.getId())){
                    Map<Integer, Map<Integer, List<ThongTinLichChieuTheoPhimDto>>> mapThongTinLichChieuCumRap = mapThongTinLichChieuHeThongRap.get(heThongRap.getId());
                    if(mapThongTinLichChieuCumRap.containsKey(cumRap.getId())){
                        Map<Integer, List<ThongTinLichChieuTheoPhimDto>> mapThongTinPhim = mapThongTinLichChieuCumRap.get(cumRap.getId());
                        if(mapThongTinPhim.containsKey(phim.getMaPhim())){
                            List<ThongTinLichChieuTheoPhimDto> thongTinLichChieuTheoPhimDtoList = mapThongTinPhim.get(phim.getMaPhim());
                            ThongTinLichChieuTheoPhimDto thongTinLichChieuTheoPhimDto = new ThongTinLichChieuTheoPhimDto();
                            thongTinLichChieuTheoPhimDto.setMaLichChieu(lichChieu.getMaLichChieu());
                            thongTinLichChieuTheoPhimDto.setMaRap(Integer.toString(rap.getMaRap()));
                            thongTinLichChieuTheoPhimDto.setTenRap(rap.getTenRap());
                            LocalDateTime ngayChieuGioChieu = LocalDateTime.parse(lichChieu.getNgayChieuGioChieu(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                            thongTinLichChieuTheoPhimDto.setNgayChieuGioChieu(ngayChieuGioChieu.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                            thongTinLichChieuTheoPhimDto.setGiaVe(lichChieu.getGiaVe());
                            thongTinLichChieuTheoPhimDtoList.add(thongTinLichChieuTheoPhimDto);
                            mapThongTinPhim.put(phim.getMaPhim(), thongTinLichChieuTheoPhimDtoList);
                        } else {
                            List<ThongTinLichChieuTheoPhimDto> thongTinLichChieuTheoPhimDtoList = new ArrayList<>();
                            ThongTinLichChieuTheoPhimDto thongTinLichChieuTheoPhimDto = new ThongTinLichChieuTheoPhimDto();
                            thongTinLichChieuTheoPhimDto.setMaLichChieu(lichChieu.getMaLichChieu());
                            thongTinLichChieuTheoPhimDto.setMaRap(Integer.toString(rap.getMaRap()));
                            thongTinLichChieuTheoPhimDto.setTenRap(rap.getTenRap());
                            LocalDateTime ngayChieuGioChieu = LocalDateTime.parse(lichChieu.getNgayChieuGioChieu(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                            thongTinLichChieuTheoPhimDto.setNgayChieuGioChieu(ngayChieuGioChieu.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                            thongTinLichChieuTheoPhimDto.setGiaVe(lichChieu.getGiaVe());
                            thongTinLichChieuTheoPhimDtoList.add(thongTinLichChieuTheoPhimDto);
                            mapThongTinPhim.put(phim.getMaPhim(), thongTinLichChieuTheoPhimDtoList);
                        }
                        mapThongTinLichChieuCumRap.put(cumRap.getId(), mapThongTinPhim);
                    } else {
                        Map<Integer, List<ThongTinLichChieuTheoPhimDto>> mapThongTinPhim = new HashMap<>();
                        List<ThongTinLichChieuTheoPhimDto> thongTinLichChieuTheoPhimDtoList = new ArrayList<>();
                        ThongTinLichChieuTheoPhimDto thongTinLichChieuTheoPhimDto = new ThongTinLichChieuTheoPhimDto();
                        thongTinLichChieuTheoPhimDto.setMaLichChieu(lichChieu.getMaLichChieu());
                        thongTinLichChieuTheoPhimDto.setMaRap(Integer.toString(rap.getMaRap()));
                        thongTinLichChieuTheoPhimDto.setTenRap(rap.getTenRap());
                        LocalDateTime ngayChieuGioChieu = LocalDateTime.parse(lichChieu.getNgayChieuGioChieu(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                        thongTinLichChieuTheoPhimDto.setNgayChieuGioChieu(ngayChieuGioChieu.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        thongTinLichChieuTheoPhimDto.setGiaVe(lichChieu.getGiaVe());
                        thongTinLichChieuTheoPhimDtoList.add(thongTinLichChieuTheoPhimDto);
                        mapThongTinPhim.put(phim.getMaPhim(), thongTinLichChieuTheoPhimDtoList);
                        mapThongTinLichChieuCumRap.put(cumRap.getId(), mapThongTinPhim);
                    }
                } else {
                    Map<Integer, Map<Integer, List<ThongTinLichChieuTheoPhimDto>>> mapThongTinLichChieuCumRap = new HashMap<>();
                    Map<Integer, List<ThongTinLichChieuTheoPhimDto>> mapThongTinPhim = new HashMap<>();
                    List<ThongTinLichChieuTheoPhimDto> thongTinLichChieuTheoPhimDtoList = new ArrayList<>();
                    ThongTinLichChieuTheoPhimDto thongTinLichChieuTheoPhimDto = new ThongTinLichChieuTheoPhimDto();
                    thongTinLichChieuTheoPhimDto.setMaLichChieu(lichChieu.getMaLichChieu());
                    thongTinLichChieuTheoPhimDto.setMaRap(Integer.toString(rap.getMaRap()));
                    thongTinLichChieuTheoPhimDto.setTenRap(rap.getTenRap());
                    LocalDateTime ngayChieuGioChieu = LocalDateTime.parse(lichChieu.getNgayChieuGioChieu(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                    thongTinLichChieuTheoPhimDto.setNgayChieuGioChieu(ngayChieuGioChieu.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    thongTinLichChieuTheoPhimDto.setGiaVe(lichChieu.getGiaVe());
                    thongTinLichChieuTheoPhimDtoList.add(thongTinLichChieuTheoPhimDto);
                    mapThongTinPhim.put(phim.getMaPhim(), thongTinLichChieuTheoPhimDtoList);
                    mapThongTinLichChieuCumRap.put(cumRap.getId(), mapThongTinPhim);
                    mapThongTinLichChieuHeThongRap.put(heThongRap.getId(), mapThongTinLichChieuCumRap);
                }
            }
            List<ThongTinLichChieuHeThongRapDto> thongTinLichChieuHeThongRapDtoList = new ArrayList<>();
            for (int idHeThongRap : mapThongTinLichChieuHeThongRap.keySet()){
                List<ThongTinLichChieuCumRapDto> thongTinLichChieuCumRapDtoList = new ArrayList<>();
                for (int idCumRap : mapThongTinLichChieuHeThongRap.get(idHeThongRap).keySet()){
                    List<ThongTinPhimDto> thongTinPhimDtoList = new ArrayList<>();
                    for (int maPhim : mapThongTinLichChieuHeThongRap.get(idHeThongRap).get(idCumRap).keySet()){
                        Phim phim = phimRepository.findById(maPhim).get();
                        List<ThongTinLichChieuTheoPhimDto> thongTinLichChieuTheoPhimDtoList = mapThongTinLichChieuHeThongRap.get(idHeThongRap).get(idCumRap).get(maPhim);
                        ThongTinPhimDto thongTinPhimDto = new ThongTinPhimDto();
                        thongTinPhimDto.setLstLichChieuTheoPhim(thongTinLichChieuTheoPhimDtoList);
                        thongTinPhimDto.setMaPhim(maPhim);
                        thongTinPhimDto.setTenPhim(phim.getTenPhim());
                        thongTinPhimDto.setHinhAnh(phim.getHinhAnh());
                        thongTinPhimDto.setHot(phim.isHot());
                        thongTinPhimDto.setDangChieu(phim.isDangChieu());
                        thongTinPhimDto.setSapChieu(phim.isSapChieu());
                        thongTinPhimDtoList.add(thongTinPhimDto);
                    }
                    CumRap cumRap = cumRapRepository.findById(idCumRap).get();
                    ThongTinLichChieuCumRapDto thongTinLichChieuCumRapDto = new ThongTinLichChieuCumRapDto();
                    thongTinLichChieuCumRapDto.setDanhSachPhim(thongTinPhimDtoList);
                    thongTinLichChieuCumRapDto.setMaCumRap(cumRap.getMaCumRap());
                    thongTinLichChieuCumRapDto.setTenCumRap(cumRap.getTenCumRap());
                    thongTinLichChieuCumRapDto.setHinhAnh("https://s3img.vcdn.vn/123phim/2021/01/bhd-star-bitexco-16105952137769.png");
                    thongTinLichChieuCumRapDto.setDiaChi(cumRap.getDiaChi());
                    thongTinLichChieuCumRapDtoList.add(thongTinLichChieuCumRapDto);
                }
                HeThongRap heThongRap = heThongRapRepository.findById(idHeThongRap).get();
                ThongTinLichChieuHeThongRapDto thongTinLichChieuHeThongRapDto = new ThongTinLichChieuHeThongRapDto();
                thongTinLichChieuHeThongRapDto.setLstCumRap(thongTinLichChieuCumRapDtoList);
                thongTinLichChieuHeThongRapDto.setMaHeThongRap(heThongRap.getMaHeThongRap());
                thongTinLichChieuHeThongRapDto.setTenHeThongRap(heThongRap.getTenHeThongRap());
                thongTinLichChieuHeThongRapDto.setLogo(heThongRap.getLogo());
                thongTinLichChieuHeThongRapDto.setMaNhom(maNhom);
                thongTinLichChieuHeThongRapDtoList.add(thongTinLichChieuHeThongRapDto);
            }
            return ResponseDto.builder()
                    .statusCode(200)
                    .message("Xử lý thành công!")
                    .content(thongTinLichChieuHeThongRapDtoList)
                    .dateTime(LocalDateTime.now().toString())
                    .build();
        } catch (Exception e) {
            logger.info("Lay thong tin lich chieu he tong rap that bai");
            logger.error(e.getMessage());
            throw new AppException(ErrorCode.NOT_FOUND);
        }
    }

    @Override
    public List<ThongTinRap> layDanhSachLichChieuTheoCumRap(int idCumRap) {
        CumRap cumRap = cumRapRepository.findById(idCumRap).get();
        List<Rap> raps = cumRap.getDanhSachRap();
        List<ThongTinRap> thongTinRaps = new ArrayList<>();
        for (Rap rap : raps) {
            Map<Integer, List<ThongTinLichChieu>> mapLichChieuTheoPhim = new HashMap<>();

            List<LichChieu> lichChieus = rap.getLichChieus();
            for (LichChieu lichChieu : lichChieus) {
                Phim phim = lichChieu.getPhim();
                ThongTinLichChieu thongTinLichChieu = new ThongTinLichChieu();
                thongTinLichChieu.setMaLichChieu(lichChieu.getMaLichChieu());
                thongTinLichChieu.setNgayChieuGioChieu(lichChieu.getNgayChieuGioChieu());
                if(mapLichChieuTheoPhim.containsKey(phim.getMaPhim())){
                    List<ThongTinLichChieu> thongTinLichChieus  = mapLichChieuTheoPhim.get(phim.getMaPhim());
                    thongTinLichChieus.add(thongTinLichChieu);
                } else {
                    List<ThongTinLichChieu> thongTinLichChieus  = new ArrayList<>();
                    thongTinLichChieus.add(thongTinLichChieu);
                    mapLichChieuTheoPhim.put(phim.getMaPhim(), thongTinLichChieus);
                }
            }
            List<ThongTinPhim> thongTinPhims = new ArrayList<>();
            for(int i : mapLichChieuTheoPhim.keySet()){
                ThongTinPhim thongTinPhim = new ThongTinPhim();
                thongTinPhim.setTenPhim(phimRepository.findById(i).get().getTenPhim());
                thongTinPhim.setHinhAnh(phimRepository.findById(i).get().getHinhAnh());
                thongTinPhim.setDanhSachLichChieu(mapLichChieuTheoPhim.get(i));
                thongTinPhims.add(thongTinPhim);
            }
            ThongTinRap thongTinRap = new ThongTinRap();
            thongTinRap.setTenRap(rap.getTenRap());
            thongTinRap.setDanhSachPhim(thongTinPhims);
            thongTinRaps.add(thongTinRap);
        }
        return thongTinRaps;
    }
}
