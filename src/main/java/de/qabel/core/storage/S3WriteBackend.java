package de.qabel.core.storage;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import de.qabel.core.exceptions.QblStorageException;

import java.io.InputStream;

class S3WriteBackend extends StorageWriteBackend {

	private final AmazonS3Client s3Client;
	private final String bucket;
	private final String prefix;

	S3WriteBackend(AWSCredentials credentials, String bucket, String prefix) {
		s3Client = new AmazonS3Client(credentials);
		this.bucket = bucket;
		this.prefix = prefix;

	}

	@Override
	void upload(String name, InputStream inputStream) throws QblStorageException {
		s3Client.putObject(bucket, prefix + '/' + name, inputStream, new ObjectMetadata());
	}

	@Override
	void delete(String name) throws QblStorageException {
		s3Client.deleteObject(bucket, prefix + '/' + name);
	}
}
