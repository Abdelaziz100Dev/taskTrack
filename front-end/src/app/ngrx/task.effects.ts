import {catchError, map, mergeMap, Observable, of} from "rxjs";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {Action} from "@ngrx/store";
import {Injectable} from "@angular/core";
import {TaskService} from "../services/task.service";
import {GetTasksFail, GetTasksSuccess, TaskActionsTypes} from "./task.actions";

@Injectable()
export class TasksEffects {
  constructor(private taskService: TaskService, private effectActions: Actions) {
  }

  getAllTasksEffect: Observable<Action> = createEffect(
    () => this.effectActions.pipe(
      ofType(TaskActionsTypes.GET_TASKS),
      mergeMap((action) => {
          return this.taskService.getTasks()
            .pipe(
              map((tasks) => new GetTasksSuccess(tasks),
                catchError((error)=>of(new GetTasksFail(error.message)))
              )
            )
        }
      )
    )
  )
}
