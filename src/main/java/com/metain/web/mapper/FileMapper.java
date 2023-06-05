package com.metain.web.mapper;

import com.metain.web.domain.File;
import com.metain.web.dto.FileDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
    public interface FileMapper {
        public int insertFile(FileDTO fileDTO);

    public int getFileId();
}
