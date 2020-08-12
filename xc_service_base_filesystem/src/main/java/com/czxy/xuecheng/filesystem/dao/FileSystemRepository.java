package com.czxy.xuecheng.filesystem.dao;

import com.czxy.xuecheng.domain.filesystem.FileSystem;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by liangtong.
 */
public interface FileSystemRepository extends MongoRepository<FileSystem,String> {
}
