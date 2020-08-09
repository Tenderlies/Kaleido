package com.tosh.kaleido.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import io.minio.GetObjectArgs;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Bucket;
import io.minio.messages.Item;

public class MinioMain {
	public static void main(String[] args) throws Exception {

		MinioClient mc = MinioClient.builder().endpoint("http://127.0.0.1:9000").credentials("minioadmin", "minioadmin")
				.build();

		List<Bucket> bs = mc.listBuckets();
		for (Bucket bucket : bs) {
			System.out.println(bucket.name());
		}

		Iterable<Result<Item>> rs = mc.listObjects(ListObjectsArgs.builder().bucket("icon").build());
		for (Result<Item> result : rs) {
			System.out.println(result.get().isDir());
			System.out.println(result.get().objectName());
		}

		InputStream is = mc.getObject(GetObjectArgs.builder().bucket("icon").object("gitlab.png").build());
		File f = new File("D:/testf.png");
		FileOutputStream fos = new FileOutputStream(f);

		int readBytes = 0;
		byte[] bytes = new byte[1024];
		while ((readBytes = is.read(bytes)) != -1) {
			fos.write(bytes, 0, readBytes);
		}
		fos.flush();
		fos.close();
		is.close();
		
		
	}

}
