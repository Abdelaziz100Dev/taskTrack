import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {TasksComponent} from "./components/tasks/tasks.component";

const routes: Routes = [
  {path : "tasks", component: TasksComponent },
  // {path : "competition/update/:id'", component: UpdateComponent},
  // {path : "competition/create", component: CreateComponent},
  // {path : "competition/:id/assign-members", component: AssignMembersComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
