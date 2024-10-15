// package dev.jeep.Lookpay.services;

// import io.supabase.StorageClient;
// import io.supabase.api.IStorageFileAPI;
// import io.supabase.data.file.*;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Service;
// import org.springframework.web.multipart.MultipartFile;

// import java.io.IOException;
// import java.util.HashMap;
// import java.util.List;
// import java.util.UUID;
// import java.util.concurrent.CompletableFuture;
// import java.util.concurrent.ExecutionException;

// @Service
// public class SupabaseStorageService {

// private final StorageClient storageClient;
// private final String BUCKET_NAME;

// public SupabaseStorageService(
// @Value("${supabase.url}") String supabaseUrl,
// @Value("${supabase.key}") String supabaseKey,
// @Value("${supabase.bucket.name}") String bucketName) {
// this.storageClient = new StorageClient(supabaseKey, supabaseUrl);
// this.BUCKET_NAME = bucketName;
// }

// public String uploadFile(MultipartFile file) throws IOException,
// ExecutionException, InterruptedException {
// String fileName = generateUniqueFileName(file.getOriginalFilename());
// IStorageFileAPI fileApi = storageClient.from(BUCKET_NAME);

// // create a File object from the MultipartFile
// java.io.File fileToUpload = java.io.File.createTempFile("temp", null);
// file.transferTo(fileToUpload);

// CompletableFuture<FilePathResponse> futureResponse = fileApi.upload(fileName,
// fileToUpload);
// FilePathResponse response = futureResponse.get(); // This blocks until the
// upload is complete

// fileToUpload.delete(); // delete the temporary file
// return response.toString();
// }

// public byte[] downloadFile(String fileName) throws IOException,
// ExecutionException, InterruptedException {
// IStorageFileAPI fileApi = storageClient.from(BUCKET_NAME);
// CompletableFuture<FileDownload> futureResponse = fileApi.download(fileName,
// null);
// FileDownload response = futureResponse.get(); // This blocks until the
// download is complete
// return response.getBytes();
// }

// public void deleteObject(String fileName) throws ExecutionException,
// InterruptedException {
// IStorageFileAPI fileApi = storageClient.from(BUCKET_NAME);
// fileApi.delete(List.of(fileName)).get(); // This blocks until the file is
// deleted
// }

// public String getFileUrl(String fileName) throws ExecutionException,
// InterruptedException {
// IStorageFileAPI fileApi = storageClient.from(BUCKET_NAME);
// FilePublicUrlResponse futureUrl = fileApi.getPublicUrl(fileName, null, null);
// return futureUrl.getPublicUrl();
// }

// public String setStoreSignature(Long storeId, MultipartFile storeSignature)
// throws IOException, ExecutionException, InterruptedException {
// String fileName = "e_signature/" + storeId + "_sign." +
// getFileExtension(storeSignature.getOriginalFilename());
// return uploadFile(storeSignature);
// }

// public HashMap<String, String> setProductImages(Long productId, MultipartFile
// productImage)
// throws IOException, ExecutionException, InterruptedException {
// HashMap<String, String> imagesRes = new HashMap<>();
// String fileName = "products/" + productId + "." +
// getFileExtension(productImage.getOriginalFilename());
// String uploadedFileName = uploadFile(productImage);
// imagesRes.put("imageKey", uploadedFileName);
// imagesRes.put("imageUrl", getFileUrl(uploadedFileName));
// return imagesRes;
// }

// private String generateUniqueFileName(String originalFilename) {
// String extension = getFileExtension(originalFilename);
// return UUID.randomUUID().toString() + "." + extension;
// }

// private String getFileExtension(String fileName) {
// return fileName != null && fileName.contains(".")
// ? fileName.substring(fileName.lastIndexOf(".") + 1)
// : "jpg";
// }

// // Método adicional para generar una URL firmada (útil para acceso temporal a
// // archivos privados)
// public String getSignedUrl(String fileName, int expirationInSeconds)
// throws ExecutionException, InterruptedException {
// IStorageFileAPI fileApi = storageClient.from(BUCKET_NAME);
// CompletableFuture<FileSignedUrlResponse> futureUrl =
// fileApi.getSignedUrl(fileName, expirationInSeconds, null,
// null);
// return futureUrl.get().getSignedUrl(); // This blocks until the signed URL is
// generated
// }

// // Método para listar archivos en un directorio específico
// public List<File> listFiles(String path) throws ExecutionException,
// InterruptedException {
// IStorageFileAPI fileApi = storageClient.from(BUCKET_NAME);
// CompletableFuture<List<File>> futureResponse = fileApi.list(path, null);
// return futureResponse.get(); // This blocks until the file list is retrieved
// }

// // Método para mover o renombrar un archivo
// public void moveFile(String sourceFilePath, String destinationFilePath)
// throws ExecutionException, InterruptedException {
// IStorageFileAPI fileApi = storageClient.from(BUCKET_NAME);
// fileApi.move(sourceFilePath, destinationFilePath).get(); // This blocks until
// the move operation is complete
// }

// // Método para copiar un archivo
// public void copyFile(String sourceFilePath, String destinationFilePath)
// throws ExecutionException, InterruptedException {
// IStorageFileAPI fileApi = storageClient.from(BUCKET_NAME);
// fileApi.copy(sourceFilePath, destinationFilePath).get(); // This blocks until
// the copy operation is complete
// }
// }