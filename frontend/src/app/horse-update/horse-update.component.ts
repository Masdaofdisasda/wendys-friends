import {Component, Input, OnInit} from '@angular/core';
import {Horse} from '../dto/horse';
import {HorseService} from '../service/horse.service';
import {Observable} from 'rxjs';
import {debounceTime, distinctUntilChanged, switchMap} from 'rxjs/operators';

@Component({
  selector: 'app-horse-update',
  templateUrl: './horse-update.component.html',
  styleUrls: ['./horse-update.component.scss']
})
export class HorseUpdateComponent implements OnInit {
  @Input() horse: Horse;
  horses: Horse[];
  success = false;
  selectedMom?: Horse;
  selectedDad?: Horse;
  mom?: Horse;
  dad?: Horse;

  constructor(
    private service: HorseService,
  ) { }

  ngOnInit(): void {
    if (this.horse.mom) {
      this.service.getHorse(this.horse.mom).subscribe(mom => this.mom = mom);
    }
    if (this.horse.dad) {
      this.service.getHorse(this.horse.dad).subscribe(dad => this.dad = dad);
    }
  }

  update(id: number, name: string, description: string, birthdate: Date, gender: string, owner: string, mom: number, dad: number): void {
    this.service.updateHorses({id, name, description, birthdate, gender, owner, mom, dad} as Horse)
      .subscribe(horse => {this.horses.push(horse);});
    this.horse=null;
    this.success = true;
    this.selectedMom=null;
    this.selectedDad=null;
  }
  searchMom = (text$: Observable<string>) => text$.pipe(
    debounceTime(200),
    distinctUntilChanged(),
    // switchMap allows returning an observable rather than maps array
    switchMap( (searchText) =>  this.service.horseLookupMom(searchText) )
  );

  searchDad = (text$: Observable<string>) => text$.pipe(
    debounceTime(200),
    distinctUntilChanged(),
    // switchMap allows returning an observable rather than maps array
    switchMap( (searchText) =>  this.service.horseLookupDad(searchText) )
  );

  /**
   * Used to format the result data from the lookup into the
   * display and list values. Maps `{name: "band", id:"id" }` into a string
   */
  resultFormatBandListValue(value: any) {
    return value.name;
  }
  /**
   * Initially binds the string value and then after selecting
   * an item by checking either for string or key/value object.
   */
  inputFormatMom(value: any)   {
    if(value.name)
    { return value.name;}
    return value;
  }
  /**
   * Initially binds the string value and then after selecting
   * an item by checking either for string or key/value object.
   */
  inputFormatDad(value: any)   {
    if(value.name)
    { return value.name;}
    return value;
  }
}
