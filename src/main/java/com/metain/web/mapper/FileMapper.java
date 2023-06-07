package com.metain.web.mapper;

import com.metain.web.domain.File;
import com.metain.web.dto.FileDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
    public interface FileMapper {
        /**
         * 파일 등록
         * */
        public int insertFile(FileDTO fileDTO);
        /**
         * 파일table의 pk가져오기 (시퀀스값)
         * */
        public int getFileId();
        /**
         * 파일 삭제 -스케쥴러사용 (30일 이후 null)
         * */
}
