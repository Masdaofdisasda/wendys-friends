import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {map, switchMap} from 'rxjs/operators';

import {Horse} from '../dto/horse';
import {HorseService} from '../service/horse.service';

@Component({
  selector: 'app-horse-detail',
  templateUrl: './horse-detail.component.html',
  styleUrls: ['./horse-detail.component.scss']
})
export class HorseDetailComponent implements OnInit {
  horse: Horse ;
  mom?: Horse;
  dad?: Horse;
  selectedHorseEdit?: Horse;
  selectedHorseDelete?: Horse;

  constructor(
    private route: ActivatedRoute,
    private service: HorseService,
    ) { }

  ngOnInit(): void {
    this.getHorse();
  }

  getHorse(): void{
    this.route.paramMap.pipe(
      switchMap((params: ParamMap) => {
        const id = +params.get('id');
        return this.service.getHorse(id);
      }),
      map(horse => this.horse = horse)
    ).subscribe(horse => {
      if (horse.mom) {
        this.service.getHorse(this.horse.mom).subscribe(mom => this.mom = mom);
      }
      if (horse.dad) {
        this.service.getHorse(this.horse.dad).subscribe(dad => this.dad = dad);
      }
    });

  }

  onSelectEdit(horse: Horse): void {
    this.selectedHorseEdit = horse;
  }

  onSelectDelete(horse: Horse): void {
    this.selectedHorseDelete = horse;
  }


}
