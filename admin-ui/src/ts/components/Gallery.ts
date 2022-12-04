import "@/style/img/logo.png";
import {Component, Prop, Vue} from "vue-property-decorator";
import {Inject} from "typescript-ioc";
import {Notification} from "element-ui";
import Toolbar from "@/ts/components/common/Toolbar";
import {ElUpload} from "element-ui/types/upload";
import {FileService} from "@/ts/services/FileService";

@Component({
    template: `
      <div class="gallery">
      <el-row type="flex" :gutter="5">
        <el-col :span="4" v-for="url in fileUrls" class="image">
          <el-button v-if="$store.getters.hasEditRights" type="danger" icon="el-icon-delete" circle class="button" @click="onDelete(url)"></el-button>
          <el-image
              :src="url"
              fit="cover"></el-image>
        </el-col>
      </el-row>
      <el-upload
          v-if="$store.getters.hasEditRights"
          style="width:300px; margin-top: 20px"
          action="/"
          :http-request="uploadImage"
          ref="upload"
          accept="image/*"
          :on-error="onUploadError"
          :on-success="onUploadSuccess"
          :before-upload="beforeUpload"
          :auto-upload="false">
        <el-button slot="trigger" size="small" type="primary">Выбрать</el-button>
        <el-button style="margin-left: 10px;" size="small" type="success" @click="onSubmit">Загрузить</el-button>
        <div class="el-upload__tip" slot="tip">Изображения не больше 2мб</div>
      </el-upload>
      </div>
    `,
    components: {
        Toolbar
    }
})
export default class Gallery extends Vue {

    $refs: {
        upload: ElUpload
    };

    /** Максимальный размер скачиваемого файла */
    private static readonly MAX_FILE_SIZE = 2 * 1024 * 1024;
    /** Сервис по работе с файлами */
    @Inject private readonly fileService: FileService;

    /** Идентификатор продукта */
    @Prop({required: true})
    private productId: number;

    /** Идентификаторы изображения продукта */
    private fileUrls: string[] = [];

    private async created() {
        await this.loadFileUrls();
    }

    /** Обработчик загрузки файлов */
    private async onSubmit() {
        await this.$refs.upload.submit();
    }

    /**
     * Метод загрузки файлов
     * @param req запрос
     */
    private async uploadImage(req) {
        const formData = new FormData();
        formData.append("file", req.file);
        await this.fileService.upload(formData, this.productId);
    }

    /**
     * Метод до загрузки
     * @param file файл
     */
    beforeUpload(file) {
        const isLt2M = file.size <= Gallery.MAX_FILE_SIZE;
        if (!isLt2M) {
            this.$message.error("Максимальный размер файла 2МБ");
        }
        return isLt2M;
    }

    /**
     * Обработчик успешной загрузки файлов
     * @param response ответ
     * @param file     файл
     * @param list     список файлов
     */
    private async onUploadSuccess(response, file, list) {
        const index = list.indexOf(file);
        if (index > -1) {
            list.splice(index, 1);
        }
        Notification.success(`Файл ${file.name} успешно загружен`);
        if (!list.length) {
            await this.loadFileUrls();
        }
    }

    /**
     * Обработчик загрузки с ошибкой
     * @param response ответ
     * @param file     загружаемый файл
     */
    private onUploadError(response, file) {
        Notification.error(`Ошибка загрузки файла ${file.name}`);
    }

    /**
     * Обработчик удаления файла
     * @param url url до файла
     */
    private async onDelete(url: string) {
        const fileId = url.split("/").pop()!;
        await this.fileService.delete(fileId);
        Notification.success("Файл удалён");
        await this.loadFileUrls();
    }

    /**
     * Загружает идентификаторы файлов
     */
    private async loadFileUrls() {
        this.fileUrls = await this.fileService.getFileUrls(this.productId);
    }
}
