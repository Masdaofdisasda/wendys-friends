import {Component, Input, OnInit} from '@angular/core';
import {HorseService} from '../service/horse.service';
import {Horse} from '../dto/horse';

@Component({
  selector: 'app-horse-delete',
  templateUrl: './horse-delete.component.html',
  styleUrls: ['./horse-delete.component.scss']
})
export class HorseDeleteComponent implements OnInit {
  @Input() horse: Horse;
  horses: Horse[];
  success = false;

  constructor(
    private service: HorseService,
  ) { }

  ngOnInit(): void {
  }

  deleteHorse(id: number){
    this.service.deleteHorse(id).subscribe();
    this.horse=null;
    this.success = true;
  }

}
