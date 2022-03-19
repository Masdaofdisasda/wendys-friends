import { Component, OnInit } from '@angular/core';
import {Horse} from '../dto/horse';
import {HorseService} from '../service/horse.service';

@Component({
  selector: 'app-horse-add',
  templateUrl: './horse-add.component.html',
  styleUrls: ['./horse-add.component.scss']
})
export class HorseAddComponent implements OnInit {
  active = false;
  horses: Horse[];

  constructor(
    private service: HorseService,
  ) { }

  ngOnInit(): void {
  }

  add(name: string, description: string, birthdate: Date, gender: string, owner: string): void {
    this.service.addHorse({name, description, birthdate, gender, owner} as Horse).subscribe(horse => {this.horses.push(horse);});
  }

}
