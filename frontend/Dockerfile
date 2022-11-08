FROM node:lts-alpine

# install simple http server for serving static content
#RUN npm install -g http-server

# make the 'app' folder the current working directory
WORKDIR ./

# copy both 'package.json' and 'package-lock.json' (if available)
COPY package*.json ./
#RUN yarn add global @vue/cli

# install project dependencies
#RUN yarn install
RUN yarn install

#RUN npm install vuetify

# copy project files and folders to the current working directory (i.e. 'app' folder)
COPY . .

RUN yarn build

# build app for production with minification

EXPOSE 80
CMD [ "yarn", "serve"]
#CMD ["npm", "run", "serve"]
#CMD [ "http-server","dist"]