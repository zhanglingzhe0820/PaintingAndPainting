import { action, computed, observable } from "mobx";

export class RegisterStore {
    @observable private step: number = 0;
    @action public nextStep = () => {
        this.step++;
    };
    @action public backStep = () => {
        this.step--;
    };

    @computed
    public get currentStep() {
        return this.step;
    }
}

export const STORE_REGISTER  = "register";

export interface RegisterProps {
  [STORE_REGISTER]?: RegisterStore
}

