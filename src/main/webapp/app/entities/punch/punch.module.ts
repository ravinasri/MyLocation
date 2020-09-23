import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyLocationSharedModule } from 'app/shared/shared.module';
import { PunchComponent } from './punch.component';
import { PunchDetailComponent } from './punch-detail.component';
import { PunchUpdateComponent } from './punch-update.component';
import { PunchDeleteDialogComponent } from './punch-delete-dialog.component';
import { punchRoute } from './punch.route';

@NgModule({
  imports: [MyLocationSharedModule, RouterModule.forChild(punchRoute)],
  declarations: [PunchComponent, PunchDetailComponent, PunchUpdateComponent, PunchDeleteDialogComponent],
  entryComponents: [PunchDeleteDialogComponent],
})
export class MyLocationPunchModule {}
