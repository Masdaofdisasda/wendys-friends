import { Component, OnInit } from '@angular/core';

import {Horse} from '../dto/horse';
import {HorseService} from '../service/horse.service';

@Component({
  selector: 'app-horse-search',
  templateUrl: './horse-search.component.html',
  styleUrls: ['./horse-search.component.scss']
})
export class HorseSearchComponent implements OnInit {
  horses: Horse[];
  name: string = null;
  description: string = null;
  birthdate: Date = null;
  gender: string = null;
  owner: string = null;
  selectedHorseEdit?: Horse;
  selectedHorseDelete?: Horse;

  constructor(
    private service: HorseService,
    ) { }

  ngOnInit(): void {
  }

  searchHorse(name: string, description: string, birthdate: Date, gender: string, owner: string){

    this.service.searchHorse({name, description, birthdate, gender, owner} as Horse)
      .subscribe((horses: Horse[]) => {this.horses = horses;});
  }

  onSelectEdit(horse: Horse): void {
    this.selectedHorseEdit = horse;
  }

  onSelectDelete(horse: Horse): void {
    this.selectedHorseDelete = horse;
  }
}
