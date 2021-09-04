import { Fragment } from 'react';
import { Redirect, Route, Switch } from 'react-router';
import CustomerFormContainer from './containers/customer/CustomerFormContainer';
import CustomerListContainer from './containers/customer/customerListContainer';
import SideMenu from './containers/sideMenu/SideMenu';

const App = (): JSX.Element => {
  return (
    <Fragment>
      <SideMenu />
      <div style={{ margin: 'auto 24vw'}}>
        <Switch>
          
          <Route exact path='/'>
            <Redirect to='/customer' />
          </Route>


          <Route path='/customer/:id/edit'>
            <CustomerFormContainer />
          </Route>

          <Route path='/customer/new'>
            <CustomerFormContainer />
          </Route>
          

          <Route path='/customer'>
            <CustomerListContainer />
          </Route>

        </Switch>
      </div>
    </Fragment>
  );
}

export default App