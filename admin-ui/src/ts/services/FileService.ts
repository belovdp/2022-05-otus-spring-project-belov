import {OnlyInstantiableByContainer, Singleton} from "typescript-ioc";
import axios from "axios";
import "@/js/env.js";

/**
 * Сервис по работе с файлами
 */
@Singleton
@OnlyInstantiableByContainer
export class FileService {

    /**
     * Загружает файл
     * @param file      файл
     * @param productId идентификатор продукта
     */
    async upload(file: FormData, productId: number): Promise<FileInfo> {
        return (await axios.post<FileInfo>(`/file-service/files/PRODUCT/${productId}`, file, {
            headers: {"Content-Type": "multipart/form-data"}
        })).data;
    }

    /**
     * Возвращает список файлов продуктов
     */
    async getFileUrls(productId: number): Promise<string[]> {
        return (await axios.get<string[]>(`/file-service/files/PRODUCT/${productId}`)).data
            .map(fileId => `${ENVS.GATEWAY_URL}/file-service/files/${fileId}`);
    }

    /**
     * Возвращает ссылку на превью
     */
    getPreviewUrl(productId: number): string {
        return `${ENVS.GATEWAY_URL}/file-service/files/PRODUCT/${productId}/preview`;
    }

    /**
     * Удаляет файл
     */
    async delete(fileId: string): Promise<void> {
        await axios.delete<void>(`/file-service/files/${fileId}`);
    }
}

/** Фильтр продуктов */
export type FileInfo = {
    /** Идентификатор */
    id: number;
}