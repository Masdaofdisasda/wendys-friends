import {Component, Input, OnInit} from '@angular/core';
import { ActivatedRoute} from '@angular/router';
import {Location} from '@angular/common';

import {Horse} from '../dto/horse';
import {HorseService} from '../service/horse.service';

@Component({
  selector: 'app-horse-detail',
  templateUrl: './horse-detail.component.html',
  styleUrls: ['./horse-detail.component.scss']
})
export class HorseDetailComponent implements OnInit {
  @Input() horse: Horse;

  constructor(
    private route: ActivatedRoute,
    private service: HorseService,
    ) { }

  ngOnInit(): void {
  }

  getHorse(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.service.getHorse(id).subscribe(horse => this.horse = horse);
  }

}
