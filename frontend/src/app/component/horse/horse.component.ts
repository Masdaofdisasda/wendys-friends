import {Component, OnInit} from '@angular/core';
import {Horse} from '../../dto/horse';
import {HorseService} from 'src/app/service/horse.service';

@Component({
  selector: 'app-horse',
  templateUrl: './horse.component.html',
  styleUrls: ['./horse.component.scss']
})
export class HorseComponent implements OnInit {
  horses: Horse[];
  selectedHorseEdit?: Horse;
  selectedHorseDelete?: Horse;
  error: string = null;

  constructor(
    private service: HorseService,
  ) { }

  ngOnInit(): void {
    this.reloadHorses();
  }

  onSelectEdit(horse: Horse): void {
    this.selectedHorseEdit = horse;
  }

  onSelectDelete(horse: Horse): void {
    this.selectedHorseDelete = horse;
  }

  reloadHorses() {
    this.service.getAll().subscribe({
      next: data => {
        console.log('received horses', data);
        this.horses = data;
      },
      error: error => {
        console.error('Error fetching horses', error.message);
        this.showError('Could not fetch horses: ' + error.message);
      }
    });
  }


  public vanishError(): void {
    this.error = null;
  }

  private showError(msg: string) {
    this.error = msg;
  }
}
