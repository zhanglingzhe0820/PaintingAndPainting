import { action, observable } from "mobx";
import { STORE_UI } from "../constants/stores";

export class UiStore {
  @observable loginModalShown: boolean;
  @observable loginModalLoading: boolean;

  @action toggleLoginModalShown = () => {
    this.loginModalShown = !this.loginModalShown;
  };

  @action setLoginModalLoading = (loading: boolean) => {
    this.loginModalLoading = loading;
  }
}

export interface UiStoreProps {
  [STORE_UI]?: UiStore;
}
