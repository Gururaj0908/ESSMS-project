import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule } from '@angular/material/menu';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatInputModule } from '@angular/material/input';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatTabsModule } from '@angular/material/tabs';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatListModule } from '@angular/material/list';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSelectModule } from '@angular/material/select';
import { MatSortModule } from '@angular/material/sort';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatRadioModule } from '@angular/material/radio';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatChipsModule } from '@angular/material/chips';
//import { MatNativeDateModule } from '@angular/material';
import { MatSliderModule } from '@angular/material/slider';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatStepperModule } from '@angular/material/stepper';
import { MatBadgeModule } from '@angular/material/badge';
//import { MatRippleModule } from '@angular/material';


import { CdkTableModule } from '@angular/cdk/table';

@NgModule({
  imports: [CdkTableModule, MatButtonModule, MatRadioModule, MatDialogModule, MatAutocompleteModule, MatBadgeModule,
    MatMenuModule, MatTableModule, MatSortModule, MatTooltipModule, MatChipsModule, MatButtonToggleModule,
    MatToolbarModule, MatPaginatorModule, MatProgressBarModule, MatSnackBarModule, MatSliderModule,
    MatIconModule, MatSelectModule, MatDatepickerModule, MatTabsModule, MatExpansionModule, //MatNativeDateModule,
    MatCardModule, MatListModule, MatCheckboxModule, MatInputModule, MatSidenavModule, MatDatepickerModule,
    MatProgressSpinnerModule, MatSlideToggleModule, MatGridListModule, MatStepperModule], //MatRippleModule],
  exports: [CdkTableModule, MatButtonModule, MatRadioModule, MatDialogModule, MatAutocompleteModule, MatBadgeModule,
    MatMenuModule, MatTableModule, MatSortModule, MatTooltipModule, MatChipsModule, MatButtonToggleModule,
    MatToolbarModule, MatPaginatorModule, MatProgressBarModule, MatSnackBarModule, MatSliderModule,
    MatIconModule, MatSelectModule, MatDatepickerModule, MatTabsModule, MatExpansionModule, //MatNativeDateModule,
    MatCardModule, MatListModule, MatCheckboxModule, MatInputModule, MatSidenavModule, MatDatepickerModule,
    MatProgressSpinnerModule, MatSlideToggleModule, MatGridListModule, MatStepperModule],// MatRippleModule],
})
export class MaterialModule { }
