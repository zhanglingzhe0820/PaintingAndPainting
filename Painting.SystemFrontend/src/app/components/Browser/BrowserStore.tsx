import { action, computed, observable, runInAction } from "mobx";
import { Tag } from "antd";
import { MissionPublicItem, MissionType } from "../../models/mission/Mission";
import React from "react";
import { missionService } from "../../api/MissionService";

interface ListDataProps {
  missionId: number,
  coverUrl: string,
  title: string,
  tags: any,
  startDate: string,
  description: string
}

const smallerDiv = {
  display: 'inline',
};

export class BrowserStore {
  private static _maxLengthOfDescription = 100;
  @observable private _paused: boolean = true;
  @observable private _reverse: boolean = true;
  @observable private _listData: ListDataProps[] = [];
  @action public reverseBrowsing = () => {
    this._reverse = !this._reverse;
    this._paused = !this._paused;
  };

  @action public search = async (info) => {
    let missions: MissionPublicItem[] = (await missionService.getAllMissions());
    runInAction(() => {
      for (let i = 0; i < missions.length; i++) {
        let tagText = [];
        tagText.push({"text": missions[i].missionType, "isTypeTag": true});
        missions[i].topics.map((item) => {
          tagText.push(item);
        });
        let listProp: ListDataProps = {
          missionId: 0,
          coverUrl: "",
          title: "",
          tags: null,
          startDate: "",
          description: ""
        };
        listProp.missionId = missions[i].missionId;
        listProp.coverUrl = missions[i].coverUrl;
        listProp.title = missions[i].title;
        listProp.tags = (
          <div>
            {tagText.map(
              (item) => {
                if (item.isTypeTag) {
                  return <Tag color="#108ee9" style={smallerDiv}>{item.text.toString()}</Tag>
                }
                else {
                  return <Tag color="geekblue" style={smallerDiv}>{item}</Tag>
                }
              }
            )}
          </div>
        );
        listProp.startDate = new Date(missions[i].start).toDateString();
        listProp.description = missions[i].description.length > BrowserStore._maxLengthOfDescription ? missions[i].description.substring(0, BrowserStore._maxLengthOfDescription) + "……" : missions[i].description;
        this._listData.push(listProp);
      }
    })
  };

  @computed get isBrowsing(): boolean {
    return !this._paused;
  }

  @computed get reverse(): boolean {
    return this._reverse;
  }

  @computed get paused(): boolean {
    return this._paused;
  }

  @computed get listData(): ListDataProps[] {
    return this._listData;
  }
}

export const STORE_BROWSER = "browser";

export interface BrowserProps {
  [STORE_BROWSER]?: BrowserStore
}
