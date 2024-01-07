import {Component, OnInit} from '@angular/core';
import {Store} from "@ngrx/store";
import {TaskState} from "../../ngrx/task.reducer";
import {map, Observable} from "rxjs";
import {MatTableDataSource} from "@angular/material/table";
import {TaskModel} from "../../model/taskModel";

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.css']
})
export class TasksComponent implements OnInit{
  taskState$:Observable<TaskState> |null = null;
  // displayedColumns: string[] = ['id', 'code', 'startTime', 'status', 'endTime', 'actions'];
  // dataSource = new MatTableDataSource<TaskModel>();

  constructor(private store:Store<any>) {
  }

  ngOnInit():void {
    this.taskState$ = this.store.pipe(
      map((state) => {
       return state.TaskState

      })
    )
  }


}
