import {TaskStatus} from "../enums/TaskStatus";

export interface TaskModel {
  id: string;
  title: string;
  description: string;
  status: TaskStatus;
}
