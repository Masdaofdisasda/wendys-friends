import {Component, OnInit} from '@angular/core';

import {Horse} from '../../dto/horse';
import {HorseService} from '../../service/horse.service';
import {Owner} from '../../dto/owner';
import {OwnerService} from '../../service/owner.service';
import {Observable} from 'rxjs';
import {debounceTime, distinctUntilChanged, switchMap} from 'rxjs/operators';

@Component({
  selector: 'app-horse-search',
  templateUrl: './horse-search.component.html',
  styleUrls: ['./horse-search.component.scss']
})
export class HorseSearchComponent implements OnInit {
  gender: string = null;
  selectedOwner?: Owner;
  selectedHorseEdit?: Horse;
  selectedHorseDelete?: Horse;
  horses: Horse[];
  owners: Owner[];

  constructor(
    private service: HorseService,
    private ownerService: OwnerService,
    ) { }

  ngOnInit(): void {

    this.ownerService.getAll().subscribe({
      next: data => {
        console.log('received horses', data);
        this.owners = data;
      }
    });
  }

  searchHorse(name: string, description: string, birthdate: Date, gender: string){
    let owner = null;
    if (this.selectedOwner){
      owner = this.selectedOwner.id;
    }
    this.service.searchHorse({name, description, birthdate, gender, owner} as Horse)
      .subscribe((horses: Horse[]) => {this.horses = horses;});
  }

  searchOwner = (text$: Observable<string>) => text$.pipe(
    debounceTime(200),
    distinctUntilChanged(),
    switchMap((searchText)=> this.ownerService.ownerLookup(searchText))
  );

  onSelectEdit(horse: Horse): void {
    this.selectedHorseEdit = horse;
  }

  onSelectDelete(horse: Horse): void {
    this.selectedHorseDelete = horse;
  }

  getDisplayName(id: number): string{
    let name;
    for (const item of this.owners) {
      if (item.id === id){
        name = item.givenname + ' ' + item.surname;
      }
    }
    return name;
  }

  resultFormatOwnerValue(value: any) {
    return value.givenname +' '+ value.surname;
  }

  inputFormatOwner(value: any)   {
    if(value.surname)
    { return value.givenname +' '+ value.surname;}
    return value;
  }

}
