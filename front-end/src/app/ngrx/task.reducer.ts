import {TaskModel} from "../model/taskModel";
import {GetTasksFail, TaskActions, TaskActionsTypes} from "./task.actions";
import {Action} from "@ngrx/store";


export enum TaskStatusEnum {
  LOADING = 'LOADING',
  LOADED = 'LOADED',
  ERROR = 'ERROR',
  INITIAL = 'INITIAL'
}
export interface TaskState {
  tasks: TaskModel[];
  error: string;
  status: TaskStatusEnum;

}

 const initialTaskState: TaskState = {
  tasks: [],
  error: '',
  status: TaskStatusEnum.INITIAL
};

export function taskReducer( state:TaskState = initialTaskState,action:Action ){

  switch (action.type) {
    case TaskActionsTypes.GET_TASKS:
      return {...state,dataState:TaskStatusEnum.LOADING}

    case TaskActionsTypes.GET_TASKS_SUCCESS:
      return {...state,dataState: TaskStatusEnum.LOADED, tasks:(<TaskActions>action).payload}

    case TaskActionsTypes.GET_TASKS_FAIL:
      return {...state,dataState:TaskStatusEnum.ERROR, errorMessage:(<TaskActions>action).payload};
    default: return {...state}

  }


}
