import {Component, Vue} from "vue-property-decorator";
import {LoginRequest} from "@/ts/services/AuthService";
import store from "@/ts/config/store";

/**
 * Компонент страницы авторизации
 */
@Component({
    template: `
      <el-row class="loginDialog" type="flex" justify="center">
      <el-card>
        <div slot="header" class="clearfix">
          <span>Вход</span>
        </div>
        <div>
          <el-form :model="loginInfo" status-icon label-width="60px">
            <el-form-item label="Логин" prop="pass">
              <el-input v-model="loginInfo.login" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="Пароль" prop="checkPass">
              <el-input type="password" v-model="loginInfo.password" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onLogin">Войти</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-card>
      </el-row>
    `
})
export default class LoginView extends Vue {

    /** Информация для авторизации */
    private loginInfo: LoginRequest = {
        login: "",
        password: ""
    };

    /**
     * Обработчик кнопки входа
     */
    private async onLogin() {
        await store.dispatch("AUTH_LOGIN", this.loginInfo);
        if (this.$route.redirectedFrom) {
            return this.$router.push(this.$route.redirectedFrom);
        }
        await this.$router.push("/");
    }
}
