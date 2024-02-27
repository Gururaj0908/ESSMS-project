import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { NavigationService } from '../services/navigation.service';
import { BusinessObject } from '../model/business.object';
import { BusinessObjectNode } from '../menu/business.object.node';
import { Node } from '../menu/node';
import { Constants } from '../../constants';
import { BusinessRouterService } from '../services/business-router.service';

@Component({
  selector: 'app-side-navigation',
  templateUrl: './side-navigation.component.html',
  styleUrls: ['./side-navigation.component.scss'],
})

export class SideNavigationComponent {
  @Input() IsMobile: boolean;

  constructor(
    public br: BusinessRouterService) {
  }

  MenuClicked(menuButton: BusinessObject, menuHeader: BusinessObject) {
    this.br.SelectedSecondaryMenu = menuHeader;
    this.br.SelectedTertiaryMenu = menuButton;
    this.br.HandleNavigation(menuButton, true, false);
  }

  IsSelected(bo: BusinessObject) {
    return this.br.SelectedTertiaryMenu != null && this.br.SelectedTertiaryMenu.id === bo.id;
  }
}
