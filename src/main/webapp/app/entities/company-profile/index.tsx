import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CompanyProfile from './company-profile';
import CompanyProfileDetail from './company-profile-detail';
import CompanyProfileUpdate from './company-profile-update';
import CompanyProfileDeleteDialog from './company-profile-delete-dialog';
import companyProfileUpdate from 'app/entities/company-profile/companyProfileUpdate';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CompanyProfileUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/create-profile`} component={companyProfileUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CompanyProfileUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit-profile`} component={companyProfileUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CompanyProfileDetail} />
      <ErrorBoundaryRoute path={match.url} component={CompanyProfile} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CompanyProfileDeleteDialog} />
  </>
);

export default Routes;
