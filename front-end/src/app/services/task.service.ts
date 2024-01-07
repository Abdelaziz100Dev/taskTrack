import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {env} from "../../enviroments/enviroment";
import {TaskModel} from "../model/taskModel";

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  constructor(private http:HttpClient) { }

   getTasks():Observable<TaskModel[]>{
    return this.http.get<TaskModel[]>(env.host+"/api/tasks") ;
   }

}
