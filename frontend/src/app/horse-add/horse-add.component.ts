import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap, catchError  } from 'rxjs/operators';
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
  selectedMom?: Horse;
  selectedDad?: Horse;

  constructor(
    private service: HorseService,
  ) { }

  ngOnInit(): void {
  }

  add(name: string, description: string, birthdate: Date, gender: string, owner: string, mom: number, dad: number): void {
    this.service.addHorse({name, description, birthdate, gender, owner, mom, dad} as Horse)
      .subscribe(horse => {this.horses.push(horse);});
    this.active=false;
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
