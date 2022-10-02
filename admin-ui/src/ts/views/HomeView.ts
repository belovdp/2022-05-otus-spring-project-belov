import "@/style/img/logo.png";
import { Component, Vue } from "vue-property-decorator";

@Component({
    template: `
    <div class="home">
      <img alt="Vue logo" src="/static/logo.png">
    </div>
  `
})
export default class HomeView extends Vue {}
