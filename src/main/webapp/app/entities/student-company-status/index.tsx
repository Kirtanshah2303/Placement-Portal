import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import StudentCompanyStatus from './student-company-status';
import StudentCompanyStatusDetail from './student-company-status-detail';
import StudentCompanyStatusUpdate from './student-company-status-update';
import StudentCompanyStatusDeleteDialog from './student-company-status-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={StudentCompanyStatusUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={StudentCompanyStatusUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={StudentCompanyStatusDetail} />
      <ErrorBoundaryRoute path={match.url} component={StudentCompanyStatus} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={StudentCompanyStatusDeleteDialog} />
  </>
);

export default Routes;
