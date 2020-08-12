package com.czxy.xuecheng.manage_media.dao;

import com.czxy.xuecheng.domain.media.MediaFile;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by liangtong.
 */
public interface MediaFileRepository extends MongoRepository<MediaFile,String> {
}
