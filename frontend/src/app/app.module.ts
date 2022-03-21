import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './component/header/header.component';
import {HttpClientModule} from '@angular/common/http';
import {NgbTypeaheadModule} from '@ng-bootstrap/ng-bootstrap';
import {FormsModule} from '@angular/forms';
import {HorseComponent} from './component/horse/horse.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { HorseDetailComponent } from './horse-detail/horse-detail.component';
import { HorseUpdateComponent } from './horse-update/horse-update.component';
import { HorseAddComponent } from './horse-add/horse-add.component';
import { HorseDeleteComponent } from './horse-delete/horse-delete.component';
import { HorseSearchComponent } from './horse-search/horse-search.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HorseComponent,
    HorseDetailComponent,
    HorseUpdateComponent,
    HorseAddComponent,
    HorseDeleteComponent,
    HorseSearchComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgbModule,
    NgbTypeaheadModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
