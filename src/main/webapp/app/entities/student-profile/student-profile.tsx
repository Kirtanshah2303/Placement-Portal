import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './student-profile.reducer';
import { IStudentProfile } from 'app/shared/model/student-profile.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const StudentProfile = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const studentProfileList = useAppSelector(state => state.studentProfile.entities);
  const loading = useAppSelector(state => state.studentProfile.loading);
  const totalItems = useAppSelector(state => state.studentProfile.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const { match } = props;

  return (
    <div>
      <h2 id="student-profile-heading" data-cy="StudentProfileHeading">
        <Translate contentKey="placementPortalApp.studentProfile.home.title">Student Profiles</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="placementPortalApp.studentProfile.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="placementPortalApp.studentProfile.home.createLabel">Create new Student Profile</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {studentProfileList && studentProfileList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="placementPortalApp.studentProfile.id">Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('studentID')}>
                  <Translate contentKey="placementPortalApp.studentProfile.studentID">Student ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('personalEmail')}>
                  <Translate contentKey="placementPortalApp.studentProfile.personalEmail">Personal Email</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('address')}>
                  <Translate contentKey="placementPortalApp.studentProfile.address">Address</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('contactNumber')}>
                  <Translate contentKey="placementPortalApp.studentProfile.contactNumber">Contact Number</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('linkedinProfile')}>
                  <Translate contentKey="placementPortalApp.studentProfile.linkedinProfile">Linkedin Profile</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dob')}>
                  <Translate contentKey="placementPortalApp.studentProfile.dob">Dob</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('location')}>
                  <Translate contentKey="placementPortalApp.studentProfile.location">Location</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('githubProfile')}>
                  <Translate contentKey="placementPortalApp.studentProfile.githubProfile">Github Profile</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('cgpa')}>
                  <Translate contentKey="placementPortalApp.studentProfile.cgpa">Cgpa</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('noOfBacklogs')}>
                  <Translate contentKey="placementPortalApp.studentProfile.noOfBacklogs">No Of Backlogs</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="placementPortalApp.studentProfile.user">User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {studentProfileList.map((studentProfile, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${studentProfile.id}`} color="link" size="sm">
                      {studentProfile.id}
                    </Button>
                  </td>
                  <td>{studentProfile.studentID}</td>
                  <td>{studentProfile.personalEmail}</td>
                  <td>{studentProfile.address}</td>
                  <td>{studentProfile.contactNumber}</td>
                  <td>{studentProfile.linkedinProfile}</td>
                  <td>
                    {studentProfile.dob ? <TextFormat type="date" value={studentProfile.dob} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{studentProfile.location}</td>
                  <td>{studentProfile.githubProfile}</td>
                  <td>{studentProfile.cgpa}</td>
                  <td>{studentProfile.noOfBacklogs}</td>
                  <td>{studentProfile.user ? studentProfile.user.login : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${studentProfile.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${studentProfile.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${studentProfile.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="placementPortalApp.studentProfile.home.notFound">No Student Profiles found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={studentProfileList && studentProfileList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default StudentProfile;
