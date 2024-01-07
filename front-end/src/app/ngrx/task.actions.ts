import {Action} from "@ngrx/store";

export enum TaskActionsTypes{
    GET_TASKS = '[TASK] Get tasks',
    GET_TASKS_FAIL = '[TASK] Get tasks fail',
    GET_TASKS_SUCCESS = '[TASK] Get tasks success',
}
export class GetTasks implements Action{
     type = TaskActionsTypes.GET_TASKS;
     constructor( public payload: any){

     }
}

export class GetTasksSuccess implements Action{
  type = TaskActionsTypes.GET_TASKS_SUCCESS;
  constructor( public payload: any){

  }
}

export class GetTasksFail implements Action{
  type = TaskActionsTypes.GET_TASKS_FAIL;
  constructor( public payload: string){

  }
}

export type TaskActions = GetTasks | GetTasksSuccess | GetTasksFail;
