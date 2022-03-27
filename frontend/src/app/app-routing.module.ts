import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HorseComponent} from './component/horse/horse.component';
import {HorseDetailComponent} from './component/horse-detail/horse-detail.component';
import {HorseSearchComponent} from './component/horse-search/horse-search.component';
import {OwnerComponent} from './component/owner/owner.component';

const routes: Routes = [
  {path: '', redirectTo: 'horses', pathMatch: 'full'},
  {path: 'horses', component: HorseComponent},
  {path: 'horses/:id', component: HorseDetailComponent },
  {path: 'search', component: HorseSearchComponent },

  {path: 'owners', component: OwnerComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
