export default [
  {
    component: './Welcome',
    icon: 'smile',
    name: '欢迎',
    path: '/welcome',
  },
  {
    path: '/users',
    routes: [
      {
        path: '/users',
        routes: [
          {
            component: './user/AccountSettings',
            icon: 'smile',
            name: '个人设置',
            path: '/users',
          },
          {
            component: './user/Login',
            path: '/users/login',
            layout: false,
            name: '登录',
          },
        ],
      },
    ],
  },
  {
    access: 'isAdmin',
    icon: 'appstore',
    path: '/oauth',
    name: 'OAuth2管理',
    routes: [
      {
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
    name: '查询表格',
    icon: 'table',
    path: '/list',
    component: './TableList',
  },
  {
    path: '/',
    redirect: '/welcome',
  },
  {
    component: './404',
  },
];
