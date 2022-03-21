import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './student-profile.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const StudentProfileDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const studentProfileEntity = useAppSelector(state => state.studentProfile.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="studentProfileDetailsHeading">
          <Translate contentKey="placementPortalApp.studentProfile.detail.title">StudentProfile</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="placementPortalApp.studentProfile.id">Id</Translate>
            </span>
          </dt>
          <dd>{studentProfileEntity.id}</dd>
          <dt>
            <span id="studentID">
              <Translate contentKey="placementPortalApp.studentProfile.studentID">Student ID</Translate>
            </span>
          </dt>
          <dd>{studentProfileEntity.studentID}</dd>
          <dt>
            <span id="personalEmail">
              <Translate contentKey="placementPortalApp.studentProfile.personalEmail">Personal Email</Translate>
            </span>
          </dt>
          <dd>{studentProfileEntity.personalEmail}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="placementPortalApp.studentProfile.address">Address</Translate>
            </span>
          </dt>
          <dd>{studentProfileEntity.address}</dd>
          <dt>
            <span id="contactNumber">
              <Translate contentKey="placementPortalApp.studentProfile.contactNumber">Contact Number</Translate>
            </span>
          </dt>
          <dd>{studentProfileEntity.contactNumber}</dd>
          <dt>
            <span id="linkedinProfile">
              <Translate contentKey="placementPortalApp.studentProfile.linkedinProfile">Linkedin Profile</Translate>
            </span>
          </dt>
          <dd>{studentProfileEntity.linkedinProfile}</dd>
          <dt>
            <span id="dob">
              <Translate contentKey="placementPortalApp.studentProfile.dob">Dob</Translate>
            </span>
          </dt>
          <dd>
            {studentProfileEntity.dob ? <TextFormat value={studentProfileEntity.dob} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="location">
              <Translate contentKey="placementPortalApp.studentProfile.location">Location</Translate>
            </span>
          </dt>
          <dd>{studentProfileEntity.location}</dd>
          <dt>
            <span id="githubProfile">
              <Translate contentKey="placementPortalApp.studentProfile.githubProfile">Github Profile</Translate>
            </span>
          </dt>
          <dd>{studentProfileEntity.githubProfile}</dd>
          <dt>
            <span id="cgpa">
              <Translate contentKey="placementPortalApp.studentProfile.cgpa">Cgpa</Translate>
            </span>
          </dt>
          <dd>{studentProfileEntity.cgpa}</dd>
          <dt>
            <span id="noOfBacklogs">
              <Translate contentKey="placementPortalApp.studentProfile.noOfBacklogs">No Of Backlogs</Translate>
            </span>
          </dt>
          <dd>{studentProfileEntity.noOfBacklogs}</dd>
          <dt>
            <Translate contentKey="placementPortalApp.studentProfile.user">User</Translate>
          </dt>
          <dd>{studentProfileEntity.user ? studentProfileEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/student-profile" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/student-profile/${studentProfileEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default StudentProfileDetail;
