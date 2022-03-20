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
  chosenMom?: number;
  chosenDad?: number;

  constructor(
    private service: HorseService,
  ) { }

  ngOnInit(): void {
  }

  update(id: number, name: string, description: string, birthdate: Date, gender: string, owner: string, mom: number, dad: number): void {
    this.service.updateHorses({id, name, description, birthdate, gender, owner, mom, dad} as Horse)
      .subscribe(horse => {this.horses.push(horse);});
    this.horse=null;
    this.success = true;
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
    { this.chosenMom = value.id;
      return value.name;}
    return value;
  }
  /**
   * Initially binds the string value and then after selecting
   * an item by checking either for string or key/value object.
   */
  inputFormatDad(value: any)   {
    if(value.name)
    { this.chosenDad = value.id;
      return value.name;}
    return value;
  }
}
