import React from 'react';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import Catalog from './pages/Catalog';
import Home from './pages/Home';
import Admin from './pages/Admin';
import Navbar from './core/assets/components/navbar';

const Routes = () => (
    <BrowserRouter>
        <Navbar>
        <Switch>
            <Route path="/" exact>
                <Home />
            </Route>
            <Route path="/catalog">
                <Catalog />
            </Route>
            <Route path="/admin">
                <Admin />
            </Route>
        </Switch>
        </Navbar>            
    </BrowserRouter>
);

export default Routes; 