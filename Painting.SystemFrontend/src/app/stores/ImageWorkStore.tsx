import { action, computed, observable, toJS } from "mobx";
import { ImageInstanceDetail } from "../models/instance/image/ImageInstanceDetail";
import { ImageJob } from "../models/instance/image/job/ImageJob";
import { ImageMissionType } from "../models/mission/image/ImageMission";

export interface ImageNotation<T extends ImageJob = ImageJob> {
  imageUrl: string;
  job: T;
}

enum TransformDirection {
  Next,
  Previous,
  Normal
}


export class ImageWorkStore {
  imageUrls: string[];
  types: ImageMissionType[];

  @observable direction: TransformDirection = TransformDirection.Normal;

  currentNotations: ImageNotation[] = [];

  initialDetail: ImageInstanceDetail;

  get currentInstanceDetail(): ImageInstanceDetail {
    const {instance} = this.initialDetail;
    return {
      imageResults: this.currentNotations.map((x, index) => ({
        id: index,
        instanceId: instance.instanceId,
        imageJob: this.currentNotations[index].job,
        url: this.currentNotations[index].imageUrl
      })),
      instance: instance
    }
  }

  @observable workIndex: number = 0;


  constructor(imageUrls: string[], types: ImageMissionType[], instanceDetail: ImageInstanceDetail) {
    this.imageUrls = imageUrls;
    this.types = types;

    this.initialDetail = instanceDetail;


    for (const url of imageUrls) {
      for (const type of types) {
        let result;
        //confirm if results exists
        if (instanceDetail.imageResults) {
          result = instanceDetail.imageResults.find(x => x.url === url && x.imageJob && x.imageJob.type === type);
        } else {
          result = null;
        }
        if (result) { //existing job, push in
          this.currentNotations.push({
            imageUrl: url,
            job: result.imageJob
          });
          // this.workIndex++; // existing job, resume progress
        } else {
          this.currentNotations.push({
            imageUrl: url,
            job: {
              type: type
            }
          });
        }
      }
    }

  }

  @computed get finished() {
    return this.workIndex === this.currentNotations.length;
  }

  get totalCount() {
    return this.currentNotations.length;
  }

  @computed get progress() {
    return this.workIndex;
  }

  @computed get currentWork(): ImageNotation {
    if (this.direction !== TransformDirection.Normal) {
      return undefined;
    }

    if (this.workIndex == this.currentNotations.length) {
      return null;
    } else {
      return this.currentNotations[this.workIndex];
    }

  }

  @action saveWork(notation: ImageNotation) {
    this.currentNotations[this.workIndex] = notation;
  }

  @action nextWork1() { // first step of going next work
    this.direction = TransformDirection.Next;
  }

  @action nextWork2() { // second step of going next work
    this.workIndex++;
    this.direction = TransformDirection.Normal;
  }

  @action previousWork1() {
    if (this.workIndex > 0) {
      this.direction = TransformDirection.Previous;
    }
  }

  @action previousWork2() {
    if (this.workIndex > 0) {
      this.workIndex--;
      this.direction = TransformDirection.Normal;
    }
  }

  @action doSecondStep() {
    if (this.direction === TransformDirection.Previous) {
      this.previousWork2();

    } else if (this.direction === TransformDirection.Next) {
      this.nextWork2();
    }
  }

}

export const STORE_IMAGEWORK = "STORE_IMAGEWORK";

export interface ImageWorkStoreProps {
  [STORE_IMAGEWORK]?: ImageWorkStore;
}
