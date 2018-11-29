import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule }    from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { EditorComponent } from './editor/editor.component';
import { PropertiesComponent } from './properties/properties.component';
import { BlocktreeComponent } from './blocktree/blocktree.component';
import { MenuComponent } from './menu/menu.component';
import { StatusComponent } from './status/status.component';
import { HardwareEditorComponent } from './hardware-editor/hardware-editor.component';

@NgModule({
  declarations: [
    AppComponent,
    EditorComponent,
    PropertiesComponent,
    BlocktreeComponent,
    MenuComponent,
    StatusComponent,
    HardwareEditorComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
