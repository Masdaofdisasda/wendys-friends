import {Component, Input, OnInit} from '@angular/core';
import {Horse} from '../dto/horse';
import {HorseService} from '../service/horse.service';

@Component({
  selector: 'app-horse-update',
  templateUrl: './horse-update.component.html',
  styleUrls: ['./horse-update.component.scss']
})
export class HorseUpdateComponent implements OnInit {
  @Input() horse: Horse;
  horses: Horse[];
  success = false;

  constructor(
    private service: HorseService,
  ) { }

  ngOnInit(): void {
  }

  update(id: number, name: string, description: string, birthdate: Date, gender: string, owner: string): void {
    this.service.updateHorses({id, name, description, birthdate, gender, owner} as Horse).subscribe(horse => {this.horses.push(horse);});
    this.horse=null;
    this.success = true;
  }
}
