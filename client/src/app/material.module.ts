import { NgModule } from "@angular/core";

import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';

const matModule = [
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatToolbarModule
]

@NgModule({
    imports: matModule,
    exports: matModule
})
export class MaterialModule {}