export default [
  {
    layout: false,
    path: '/users',
    routes: [
      {
        path: '/users',
        routes: [
          { component: './user/Setting', name: '个人设置', path: '/users/settings' },
          { component: './user/Login', name: '登录', path: '/users/login' },
        ],
      },
    ],
  },
  { component: './Welcome', icon: 'smile', name: '欢迎', path: '/welcome' },
  {
    icon: 'appstore',
    path: '/oauth',
    name: 'OAuth2管理',
    routes: [
      {
        access: 'isAdmin',
        component: './oauth/Application',
        name: '应用管理',
        path: '/oauth/applications',
      },
      {
        component: './oauth/Approve',
        name: 'OAuth2授权',
        path: '/oauth/approve',
        hideInMenu: true,
        headerRender: false,
        menuRender: false,
      },
    ],
  },
  {
    component: './TableList',
    icon: 'table',
    name: '查询表格',
    path: '/list',
  },
  {
    path: '/',
    redirect: '/welcome',
  },
  {
    component: './404',
  },
];
