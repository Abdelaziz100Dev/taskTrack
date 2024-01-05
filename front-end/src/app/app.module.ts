import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MdbCheckboxModule} from "mdb-angular-ui-kit/checkbox";
import {MatInputModule} from "@angular/material/input";
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {MatDialogModule} from "@angular/material/dialog";
import {MatButtonModule} from "@angular/material/button";
import {StoreModule} from "@ngrx/store";
import {EffectsModule} from "@ngrx/effects";
import {StoreDevtoolsModule} from "@ngrx/store-devtools";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    // NgOptimizedImage,
    BrowserAnimationsModule,
    MdbCheckboxModule,
    MatInputModule,
    MatAutocompleteModule,
    MatDialogModule,
    MatButtonModule,
      //Ngrx modules
      StoreModule.forRoot([]),
      EffectsModule.forRoot([]),
      StoreDevtoolsModule.instrument({}) // for debugging purposes only in development mode (remove in production), it gives us a nice UI to see the state of our store

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
